package com.sportiq.sportiq.repository;

import com.sportiq.sportiq.domain.MembershipRole;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MembershipRole entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembershipRoleRepository extends JpaRepository<MembershipRole, Long> {
}
