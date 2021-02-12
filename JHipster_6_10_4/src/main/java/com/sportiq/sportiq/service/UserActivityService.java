package com.sportiq.sportiq.service;

import com.sportiq.sportiq.service.dto.UserActivityDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.sportiq.sportiq.domain.UserActivity}.
 */
public interface UserActivityService {

    /**
     * Save a userActivity.
     *
     * @param userActivityDTO the entity to save.
     * @return the persisted entity.
     */
    UserActivityDTO save(UserActivityDTO userActivityDTO);

    /**
     * Get all the userActivities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserActivityDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userActivity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserActivityDTO> findOne(Long id);

    /**
     * Delete the "id" userActivity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
