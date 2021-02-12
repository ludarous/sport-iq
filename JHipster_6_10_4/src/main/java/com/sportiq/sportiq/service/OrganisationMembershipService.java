package com.sportiq.sportiq.service;

import com.sportiq.sportiq.service.dto.OrganisationMembershipDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.sportiq.sportiq.domain.OrganisationMembership}.
 */
public interface OrganisationMembershipService {

    /**
     * Save a organisationMembership.
     *
     * @param organisationMembershipDTO the entity to save.
     * @return the persisted entity.
     */
    OrganisationMembershipDTO save(OrganisationMembershipDTO organisationMembershipDTO);

    /**
     * Get all the organisationMemberships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrganisationMembershipDTO> findAll(Pageable pageable);

    /**
     * Get all the organisationMemberships with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<OrganisationMembershipDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" organisationMembership.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrganisationMembershipDTO> findOne(Long id);

    /**
     * Delete the "id" organisationMembership.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
