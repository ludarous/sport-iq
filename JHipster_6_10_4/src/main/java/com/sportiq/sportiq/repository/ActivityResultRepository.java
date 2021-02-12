package com.sportiq.sportiq.repository;

import com.sportiq.sportiq.domain.ActivityResult;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ActivityResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityResultRepository extends JpaRepository<ActivityResult, Long> {
}
