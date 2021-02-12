package com.sportiq.sportiq.service;

import com.sportiq.sportiq.service.dto.UserActivityResultSplitDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.sportiq.sportiq.domain.UserActivityResultSplit}.
 */
public interface UserActivityResultSplitService {

    /**
     * Save a userActivityResultSplit.
     *
     * @param userActivityResultSplitDTO the entity to save.
     * @return the persisted entity.
     */
    UserActivityResultSplitDTO save(UserActivityResultSplitDTO userActivityResultSplitDTO);

    /**
     * Get all the userActivityResultSplits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserActivityResultSplitDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userActivityResultSplit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserActivityResultSplitDTO> findOne(Long id);

    /**
     * Delete the "id" userActivityResultSplit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
