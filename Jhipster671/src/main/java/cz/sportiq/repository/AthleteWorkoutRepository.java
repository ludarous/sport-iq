package cz.sportiq.repository;

import cz.sportiq.domain.AthleteWorkout;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the AthleteWorkout entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AthleteWorkoutRepository extends JpaRepository<AthleteWorkout, Long> {

    Optional<AthleteWorkout> findByWorkoutIdAndAthleteEventId(Long workoutId, Long athleteEventId);
}
