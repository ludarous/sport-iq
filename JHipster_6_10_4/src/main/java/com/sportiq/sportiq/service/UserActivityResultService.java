package com.sportiq.sportiq.service;

import com.sportiq.sportiq.service.dto.UserActivityResultDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.sportiq.sportiq.domain.UserActivityResult}.
 */
public interface UserActivityResultService {

    /**
     * Save a userActivityResult.
     *
     * @param userActivityResultDTO the entity to save.
     * @return the persisted entity.
     */
    UserActivityResultDTO save(UserActivityResultDTO userActivityResultDTO);

    /**
     * Get all the userActivityResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserActivityResultDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userActivityResult.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserActivityResultDTO> findOne(Long id);

    /**
     * Delete the "id" userActivityResult.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
