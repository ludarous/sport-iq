package cz.sportiq.service;

import cz.sportiq.service.dto.EventLocationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link cz.sportiq.domain.EventLocation}.
 */
public interface EventLocationService {

    /**
     * Save a eventLocation.
     *
     * @param eventLocationDTO the entity to save.
     * @return the persisted entity.
     */
    EventLocationDTO save(EventLocationDTO eventLocationDTO);

    /**
     * Get all the eventLocations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EventLocationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" eventLocation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventLocationDTO> findOne(Long id);

    /**
     * Delete the "id" eventLocation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
