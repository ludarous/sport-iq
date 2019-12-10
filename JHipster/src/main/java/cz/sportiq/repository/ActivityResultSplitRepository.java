package cz.sportiq.repository;

import cz.sportiq.domain.ActivityResultSplit;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ActivityResultSplit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActivityResultSplitRepository extends JpaRepository<ActivityResultSplit, Long> {

}
