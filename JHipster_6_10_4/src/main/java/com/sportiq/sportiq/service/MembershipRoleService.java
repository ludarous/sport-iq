package com.sportiq.sportiq.service;

import com.sportiq.sportiq.service.dto.MembershipRoleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.sportiq.sportiq.domain.MembershipRole}.
 */
public interface MembershipRoleService {

    /**
     * Save a membershipRole.
     *
     * @param membershipRoleDTO the entity to save.
     * @return the persisted entity.
     */
    MembershipRoleDTO save(MembershipRoleDTO membershipRoleDTO);

    /**
     * Get all the membershipRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MembershipRoleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" membershipRole.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MembershipRoleDTO> findOne(Long id);

    /**
     * Delete the "id" membershipRole.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
