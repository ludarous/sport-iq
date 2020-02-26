package cz.sportiq.repository;

import cz.sportiq.domain.Workout;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Workout entity.
 */
@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    @Query(value = "select distinct workout from Workout workout left join fetch workout.activities left join fetch workout.categories left join fetch workout.sports",
        countQuery = "select count(distinct workout) from Workout workout")
    Page<Workout> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct workout from Workout workout left join fetch workout.activities left join fetch workout.categories left join fetch workout.sports")
    List<Workout> findAllWithEagerRelationships();

    @Query("select workout from Workout workout left join fetch workout.activities left join fetch workout.categories left join fetch workout.sports where workout.id =:id")
    Optional<Workout> findOneWithEagerRelationships(@Param("id") Long id);

}
