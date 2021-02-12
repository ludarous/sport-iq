package com.sportiq.sportiq.service;

import com.sportiq.sportiq.service.dto.OrganisationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.sportiq.sportiq.domain.Organisation}.
 */
public interface OrganisationService {

    /**
     * Save a organisation.
     *
     * @param organisationDTO the entity to save.
     * @return the persisted entity.
     */
    OrganisationDTO save(OrganisationDTO organisationDTO);

    /**
     * Get all the organisations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganisationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" organisation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganisationDTO> findOne(Long id);

    /**
     * Delete the "id" organisation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
