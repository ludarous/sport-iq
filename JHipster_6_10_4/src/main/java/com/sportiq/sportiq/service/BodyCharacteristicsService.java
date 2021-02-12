package com.sportiq.sportiq.service;

import com.sportiq.sportiq.service.dto.BodyCharacteristicsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.sportiq.sportiq.domain.BodyCharacteristics}.
 */
public interface BodyCharacteristicsService {

    /**
     * Save a bodyCharacteristics.
     *
     * @param bodyCharacteristicsDTO the entity to save.
     * @return the persisted entity.
     */
    BodyCharacteristicsDTO save(BodyCharacteristicsDTO bodyCharacteristicsDTO);

    /**
     * Get all the bodyCharacteristics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BodyCharacteristicsDTO> findAll(Pageable pageable);


    /**
     * Get the "id" bodyCharacteristics.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BodyCharacteristicsDTO> findOne(Long id);

    /**
     * Delete the "id" bodyCharacteristics.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
