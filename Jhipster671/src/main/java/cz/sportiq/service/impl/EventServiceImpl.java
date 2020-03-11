package cz.sportiq.service.impl;

import cz.sportiq.domain.Athlete;
import cz.sportiq.domain.Event;
import cz.sportiq.domain.User;
import cz.sportiq.repository.AthleteRepository;
import cz.sportiq.repository.EventRepository;
import cz.sportiq.repository.UserRepository;
import cz.sportiq.security.SecurityUtils;
import cz.sportiq.service.EventService;
import cz.sportiq.service.dto.EventDTO;
import cz.sportiq.service.mapper.EventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * Service Implementation for managing {@link Event}.
 */
@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    private final EventRepository eventRepository;

    private final EventMapper eventMapper;

    private final UserRepository userRepository;

    private final AthleteRepository athleteRepository;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper, UserRepository userRepository, AthleteRepository athleteRepository) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.userRepository = userRepository;
        this.athleteRepository = athleteRepository;
    }

    /**
     * Save a event.
     *
     * @param eventDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EventDTO save(EventDTO eventDTO) {
        log.debug("Request to save Event : {}", eventDTO);
        Event event = eventMapper.toEntity(eventDTO);
        event = eventRepository.save(event);
        EventDTO result = eventMapper.toDto(event);
        return result;
    }

    /**
     * Get all the events.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Events");
        return eventRepository.findAll(pageable)
            .map(eventMapper::toDto);
    }

    /**
     * Get all the events with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EventDTO> findAllWithEagerRelationships(Pageable pageable) {
        return eventRepository.findAllWithEagerRelationships(pageable).map(eventMapper::toDto);
    }


    /**
     * Get one event by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EventDTO> findOne(Long id) {
        log.debug("Request to get Event : {}", id);
        return eventRepository.findOneWithEagerRelationships(id)
            .map(eventMapper::toDto);
    }

    /**
     * Delete the event by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Event : {}", id);
        eventRepository.deleteById(id);
    }

    @Override
    public EventDTO signToEvent(Long id) throws Exception {
        Optional<String> currentUserLoginOpt = SecurityUtils.getCurrentUserLogin();
        Optional<Event> eventOpt = eventRepository.findById(id);

        if(!currentUserLoginOpt.isPresent()) {
            throw new Exception("User not logged in!");
        }

        if(!eventOpt.isPresent()) {
            throw new Exception("Event not found!");
        }

        Optional<User> currentUserOpt = userRepository.findOneByLogin(currentUserLoginOpt.get());
        if(!currentUserOpt.isPresent()) {
            throw new Exception("User not found!");
        }

        Optional<Athlete> athleteOpt = athleteRepository.findOneByUserId(currentUserOpt.get().getId());
        Athlete athlete;
        Event event = eventOpt.get();

        if(!athleteOpt.isPresent()) {
            User currentUser = currentUserOpt.get();
            athlete = new Athlete()
                .email(currentUser.getEmail())
                .firstName(currentUser.getFirstName())
                .lastName(currentUser.getLastName())
                .user(currentUser);
            athlete = athleteRepository.save(athlete);
        } else {
            athlete = athleteOpt.get();
        }

        if(!event.getAthletes().contains(athlete)) {
            event.addAthletes(athlete);
            event = eventRepository.save(event);
        }

        return eventMapper.toDto(event);

    }
}
