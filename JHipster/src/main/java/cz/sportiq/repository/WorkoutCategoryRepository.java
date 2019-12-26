package cz.sportiq.repository;
import cz.sportiq.domain.WorkoutCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the WorkoutCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkoutCategoryRepository extends JpaRepository<WorkoutCategory, Long> {

}
