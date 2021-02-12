package com.sportiq.sportiq.repository;

import com.sportiq.sportiq.domain.OrganisationMembership;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the OrganisationMembership entity.
 */
@Repository
public interface OrganisationMembershipRepository extends JpaRepository<OrganisationMembership, Long> {

    @Query("select organisationMembership from OrganisationMembership organisationMembership where organisationMembership.user.login = ?#{principal.preferredUsername}")
    List<OrganisationMembership> findByUserIsCurrentUser();

    @Query(value = "select distinct organisationMembership from OrganisationMembership organisationMembership left join fetch organisationMembership.roles",
        countQuery = "select count(distinct organisationMembership) from OrganisationMembership organisationMembership")
    Page<OrganisationMembership> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct organisationMembership from OrganisationMembership organisationMembership left join fetch organisationMembership.roles")
    List<OrganisationMembership> findAllWithEagerRelationships();

    @Query("select organisationMembership from OrganisationMembership organisationMembership left join fetch organisationMembership.roles where organisationMembership.id =:id")
    Optional<OrganisationMembership> findOneWithEagerRelationships(@Param("id") Long id);
}
