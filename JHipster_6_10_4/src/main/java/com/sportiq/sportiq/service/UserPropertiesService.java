package com.sportiq.sportiq.service;

import com.sportiq.sportiq.service.dto.UserPropertiesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.sportiq.sportiq.domain.UserProperties}.
 */
public interface UserPropertiesService {

    /**
     * Save a userProperties.
     *
     * @param userPropertiesDTO the entity to save.
     * @return the persisted entity.
     */
    UserPropertiesDTO save(UserPropertiesDTO userPropertiesDTO);

    /**
     * Get all the userProperties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserPropertiesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userProperties.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserPropertiesDTO> findOne(Long id);

    /**
     * Delete the "id" userProperties.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
