package cz.sportiq.repository;

import cz.sportiq.domain.ActivityResultSplit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ActivityResultSplit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityResultSplitRepository extends JpaRepository<ActivityResultSplit, Long> {

}
