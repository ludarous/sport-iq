package com.sportiq.sportiq.service;

import com.sportiq.sportiq.service.dto.UserEventDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.sportiq.sportiq.domain.UserEvent}.
 */
public interface UserEventService {

    /**
     * Save a userEvent.
     *
     * @param userEventDTO the entity to save.
     * @return the persisted entity.
     */
    UserEventDTO save(UserEventDTO userEventDTO);

    /**
     * Get all the userEvents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserEventDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userEvent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserEventDTO> findOne(Long id);

    /**
     * Delete the "id" userEvent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
