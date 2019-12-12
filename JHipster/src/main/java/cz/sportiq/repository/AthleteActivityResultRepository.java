package cz.sportiq.repository;

import cz.sportiq.domain.AthleteActivityResult;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the AthleteActivityResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AthleteActivityResultRepository extends JpaRepository<AthleteActivityResult, Long> {

    Optional<AthleteActivityResult> findByActivityResultIdAndAthleteActivityId(Long activityResultId, Long athleteActivityId);

    @Query("select athleteActivityResult from AthleteActivityResult athleteActivityResult " +
        "join athleteActivityResult.athleteActivity athleteActivity " +
        "join athleteActivity.athleteWorkout athleteWorkout " +
        "join athleteWorkout.athleteEvent athleteEvent " +
        "where athleteActivityResult.activityResult.id = :activityResultId and athleteEvent.event.id = :eventId")
    List<AthleteActivityResult> findActivityResultsByEventId(@Param("activityResultId") Long activityResultId, @Param("eventId") Long eventId);
}
