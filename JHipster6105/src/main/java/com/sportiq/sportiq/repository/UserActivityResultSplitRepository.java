package com.sportiq.sportiq.repository;

import com.sportiq.sportiq.domain.UserActivityResultSplit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserActivityResultSplit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserActivityResultSplitRepository extends JpaRepository<UserActivityResultSplit, Long> {
}
