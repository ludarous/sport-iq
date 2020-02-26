package cz.sportiq.repository;

import cz.sportiq.domain.AthleteActivityResultSplit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AthleteActivityResultSplit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AthleteActivityResultSplitRepository extends JpaRepository<AthleteActivityResultSplit, Long> {

}
