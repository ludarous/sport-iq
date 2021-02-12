package com.sportiq.sportiq.repository;

import com.sportiq.sportiq.domain.UserActivity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {
}
