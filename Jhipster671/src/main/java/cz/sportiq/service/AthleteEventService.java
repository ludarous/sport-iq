package cz.sportiq.service;

import cz.sportiq.domain.Athlete;
import cz.sportiq.domain.AthleteEvent;
import cz.sportiq.domain.Event;
import cz.sportiq.service.dto.AthleteEventDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link cz.sportiq.domain.AthleteEvent}.
 */
public interface AthleteEventService {

    /**
     * Save a athleteEvent.
     *
     * @param athleteEventDTO the entity to save.
     * @return the persisted entity.
     */
    AthleteEventDTO save(AthleteEventDTO athleteEventDTO);

    /**
     * Get all the athleteEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AthleteEventDTO> findAll(Pageable pageable);


    /**
     * Get the "id" athleteEvent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AthleteEventDTO> findOne(Long id);

    /**
     * Get the "id" athleteEvent.
     *
     * @param eventId the id of the entity
     * @return the entity
     */
    AthleteEventDTO findByEventIdAndAthleteId(Long eventId, Long athleteId);

    /**
     * Get the "id" athleteEvent.
     *
     * @param eventId the id of the event
     * @return the entity
     */
    List<AthleteEventDTO> findAllByEventId(Long eventId);

    @Transactional(readOnly = true)
    AthleteEvent findOrCreateAthleteEvent(Event event, Athlete athlete);

    /**
     * Delete the "id" athleteEvent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<AthleteEventDTO> findAllByAthleteId(Long athleteId);
}
