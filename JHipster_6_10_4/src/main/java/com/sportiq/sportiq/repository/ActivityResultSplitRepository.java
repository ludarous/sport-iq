package com.sportiq.sportiq.repository;

import com.sportiq.sportiq.domain.ActivityResultSplit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ActivityResultSplit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityResultSplitRepository extends JpaRepository<ActivityResultSplit, Long> {
}
