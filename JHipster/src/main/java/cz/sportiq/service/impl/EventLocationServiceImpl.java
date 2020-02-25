package cz.sportiq.service.impl;

import cz.sportiq.service.EventLocationService;
import cz.sportiq.domain.EventLocation;
import cz.sportiq.repository.EventLocationRepository;
import cz.sportiq.repository.search.EventLocationSearchRepository;
import cz.sportiq.service.dto.EventLocationDTO;
import cz.sportiq.service.mapper.EventLocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link EventLocation}.
 */
@Service
@Transactional
public class EventLocationServiceImpl implements EventLocationService {

    private final Logger log = LoggerFactory.getLogger(EventLocationServiceImpl.class);

    private final EventLocationRepository eventLocationRepository;

    private final EventLocationMapper eventLocationMapper;

    private final EventLocationSearchRepository eventLocationSearchRepository;

    public EventLocationServiceImpl(EventLocationRepository eventLocationRepository, EventLocationMapper eventLocationMapper, EventLocationSearchRepository eventLocationSearchRepository) {
        this.eventLocationRepository = eventLocationRepository;
        this.eventLocationMapper = eventLocationMapper;
        this.eventLocationSearchRepository = eventLocationSearchRepository;
    }

    /**
     * Save a eventLocation.
     *
     * @param eventLocationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EventLocationDTO save(EventLocationDTO eventLocationDTO) {
        log.debug("Request to save EventLocation : {}", eventLocationDTO);
        EventLocation eventLocation = eventLocationMapper.toEntity(eventLocationDTO);
        eventLocation = eventLocationRepository.save(eventLocation);
        EventLocationDTO result = eventLocationMapper.toDto(eventLocation);
        eventLocationSearchRepository.save(eventLocation);
        return result;
    }

    /**
     * Get all the eventLocations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventLocationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventLocations");
        return eventLocationRepository.findAll(pageable)
            .map(eventLocationMapper::toDto);
    }


    /**
     * Get one eventLocation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EventLocationDTO> findOne(Long id) {
        log.debug("Request to get EventLocation : {}", id);
        return eventLocationRepository.findById(id)
            .map(eventLocationMapper::toDto);
    }

    /**
     * Delete the eventLocation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventLocation : {}", id);
        eventLocationRepository.deleteById(id);
        eventLocationSearchRepository.deleteById(id);
    }

    /**
     * Search for the eventLocation corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventLocationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EventLocations for query {}", query);
        return eventLocationSearchRepository.search(queryStringQuery(query), pageable)
            .map(eventLocationMapper::toDto);
    }
}
