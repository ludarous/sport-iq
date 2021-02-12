package com.sportiq.sportiq.repository;

import com.sportiq.sportiq.domain.BodyCharacteristics;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BodyCharacteristics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BodyCharacteristicsRepository extends JpaRepository<BodyCharacteristics, Long> {
}
