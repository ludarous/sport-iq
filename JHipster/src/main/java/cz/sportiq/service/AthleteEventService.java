package cz.sportiq.service;

import cz.sportiq.service.dto.AthleteEventDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AthleteEvent.
 */
public interface AthleteEventService {

    /**
     * Save a athleteEvent.
     *
     * @param athleteEventDTO the entity to save
     * @return the persisted entity
     */
    AthleteEventDTO save(AthleteEventDTO athleteEventDTO);

    /**
     * Get all the athleteEvents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AthleteEventDTO> findAll(Pageable pageable);


    /**
     * Get the "id" athleteEvent.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AthleteEventDTO> findOne(Long id);

    /**
     * Get the "id" athleteEvent.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AthleteEventDTO findByEventIdAndAthleteId(Long eventId, Long athleteId);

    /**
     * Delete the "id" athleteEvent.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the athleteEvent corresponding to the query.
     *
     * @param query the query of the search
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AthleteEventDTO> search(String query, Pageable pageable);
}
