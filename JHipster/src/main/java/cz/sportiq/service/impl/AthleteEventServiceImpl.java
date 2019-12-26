package cz.sportiq.service.impl;

import cz.sportiq.domain.*;
import cz.sportiq.repository.AthleteRepository;
import cz.sportiq.repository.EventRepository;
import cz.sportiq.service.AthleteEventService;
import cz.sportiq.repository.AthleteEventRepository;
import cz.sportiq.repository.search.AthleteEventSearchRepository;
import cz.sportiq.service.AthleteWorkoutService;
import cz.sportiq.service.dto.AthleteEventDTO;
import cz.sportiq.service.mapper.AthleteEventMapper;
import cz.sportiq.service.mapper.full.AthleteEventFullMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link AthleteEvent}.
 */
@Service
@Transactional
public class AthleteEventServiceImpl implements AthleteEventService {

    private final Logger log = LoggerFactory.getLogger(AthleteEventServiceImpl.class);

    private final AthleteEventRepository athleteEventRepository;

    private final AthleteEventMapper athleteEventMapper;

    private final AthleteEventFullMapper athleteEventFullMapper;

    private final AthleteEventSearchRepository athleteEventSearchRepository;

    private final EventRepository eventRepository;

    private final AthleteRepository athleteRepository;

    private final AthleteWorkoutService athleteWorkoutService;

    public AthleteEventServiceImpl(AthleteEventRepository athleteEventRepository, AthleteEventMapper athleteEventMapper, AthleteEventFullMapper athleteEventFullMapper, AthleteEventSearchRepository athleteEventSearchRepository, EventRepository eventRepository, AthleteRepository athleteRepository, AthleteWorkoutService athleteWorkoutService) {
        this.athleteEventRepository = athleteEventRepository;
        this.athleteEventMapper = athleteEventMapper;
        this.athleteEventFullMapper = athleteEventFullMapper;
        this.athleteEventSearchRepository = athleteEventSearchRepository;
        this.eventRepository = eventRepository;
        this.athleteRepository = athleteRepository;
        this.athleteWorkoutService = athleteWorkoutService;
    }

    /**
     * Save a athleteEvent.
     *
     * @param athleteEventDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AthleteEventDTO save(AthleteEventDTO athleteEventDTO) {
        log.debug("Request to save AthleteEvent : {}", athleteEventDTO);
        AthleteEvent athleteEvent = athleteEventMapper.toEntity(athleteEventDTO);
        athleteEvent = athleteEventRepository.save(athleteEvent);
        AthleteEventDTO result = athleteEventMapper.toDto(athleteEvent);
        athleteEventSearchRepository.save(athleteEvent);
        return result;
    }

    /**
     * Get all the athleteEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AthleteEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AthleteEvents");
        return athleteEventRepository.findAll(pageable)
            .map(athleteEventMapper::toDto);
    }


    /**
     * Get one athleteEvent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AthleteEventDTO> findOne(Long id) {
        log.debug("Request to get AthleteEvent : {}", id);
        return athleteEventRepository.findById(id)
            .map(athleteEventMapper::toDto);
    }

    @Override
    public AthleteEventDTO findByEventIdAndAthleteId(Long eventId, Long athleteId) {

        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (!eventOptional.isPresent()) {
            log.warn("Cannot find event with id {} when try to find athleteEvent", eventId);
            return null;
        }

        Optional<Athlete> athleteOptional = athleteRepository.findById(athleteId);
        if (!athleteOptional.isPresent()) {
            log.warn("Cannot find athlete with id {} when try to find athleteEvent", athleteId);
            return null;
        }


        AthleteEvent athleteEvent = findOrCreateAthleteEvent(eventOptional.get(), athleteOptional.get());
        return athleteEventFullMapper.toDto(athleteEvent);

    }

    @Override
    public List<AthleteEventDTO> findAllByEventId(Long eventId) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (!eventOptional.isPresent()) {
            log.warn("Cannot find event with id {} when try to find all event's athleteEvents", eventId);
            return null;
        }

        Event event = eventOptional.get();
        List<AthleteEventDTO> athleteEventDTOS = new ArrayList<>();
        for (Athlete athlete : event.getAthletes()) {
            athleteEventDTOS.add(findByEventIdAndAthleteId(eventId, athlete.getId()));
        }
        return athleteEventDTOS;
    }


    @Override
    @Transactional(readOnly = true)
    public AthleteEvent findOrCreateAthleteEvent(Event event, Athlete athlete) {
        AthleteEvent athleteEvent;
        Optional<AthleteEvent> athleteEventOptional = athleteEventRepository.findByEventIdAndAthleteId(event.getId(), athlete.getId());
        if (athleteEventOptional.isPresent()) {
            athleteEvent = athleteEventOptional.get();
        } else {
            athleteEvent = new AthleteEvent();
            athleteEvent.setEvent(event);
            athleteEvent.setAthlete(athlete);
        }

        athleteEvent = athleteEventRepository.saveAndFlush(athleteEvent);

        for (Workout workout : event.getTests()) {
            AthleteWorkout athleteWorkout = athleteWorkoutService.findOrCreateAthleteWorkout(workout, athleteEvent);
            athleteEvent.addAthleteWorkouts(athleteWorkout);
        }

        return athleteEventRepository.save(athleteEvent);
    }

    /**
     * Delete the athleteEvent by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AthleteEvent : {}", id);
        athleteEventRepository.deleteById(id);
        athleteEventSearchRepository.deleteById(id);
    }

    /**
     * Search for the athleteEvent corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AthleteEventDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AthleteEvents for query {}", query);
        return athleteEventSearchRepository.search(queryStringQuery(query), pageable)
            .map(athleteEventMapper::toDto);
    }

    @Override
    public List<AthleteEventDTO> findAllByAthleteId(Long athleteId) {
        Optional<Athlete> athleteOptional = athleteRepository.findById(athleteId);
        if (!athleteOptional.isPresent()) {
            log.warn("Cannot find athlete with id {} when try to find all event's athleteEvents", athleteId);
            return null;
        }

        List<AthleteEvent> athleteEvents = this.athleteEventRepository.findByAthleteId(athleteId).stream().collect(Collectors.toList());
        return this.athleteEventMapper.toDto(athleteEvents);
    }
}
