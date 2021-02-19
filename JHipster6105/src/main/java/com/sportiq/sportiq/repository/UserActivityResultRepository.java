package com.sportiq.sportiq.repository;

import com.sportiq.sportiq.domain.UserActivityResult;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserActivityResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserActivityResultRepository extends JpaRepository<UserActivityResult, Long> {
}
