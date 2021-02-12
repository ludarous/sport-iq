package com.sportiq.sportiq.service;

import com.sportiq.sportiq.service.dto.GroupMembershipDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.sportiq.sportiq.domain.GroupMembership}.
 */
public interface GroupMembershipService {

    /**
     * Save a groupMembership.
     *
     * @param groupMembershipDTO the entity to save.
     * @return the persisted entity.
     */
    GroupMembershipDTO save(GroupMembershipDTO groupMembershipDTO);

    /**
     * Get all the groupMemberships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GroupMembershipDTO> findAll(Pageable pageable);

    /**
     * Get all the groupMemberships with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<GroupMembershipDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" groupMembership.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GroupMembershipDTO> findOne(Long id);

    /**
     * Delete the "id" groupMembership.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
