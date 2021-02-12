package com.sportiq.sportiq.repository;

import com.sportiq.sportiq.domain.Organisation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Organisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

    @Query("select organisation from Organisation organisation where organisation.owner.login = ?#{principal.preferredUsername}")
    List<Organisation> findByOwnerIsCurrentUser();
}
