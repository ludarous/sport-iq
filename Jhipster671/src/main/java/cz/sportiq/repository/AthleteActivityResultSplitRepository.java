package cz.sportiq.repository;

import cz.sportiq.domain.AthleteActivityResultSplit;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the AthleteActivityResultSplit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AthleteActivityResultSplitRepository extends JpaRepository<AthleteActivityResultSplit, Long> {

    Optional<AthleteActivityResultSplit> findByActivityResultSplitIdAndAthleteActivityResultId(Long activityResultSplitId, Long athleteActivityResultId);

    @Query("select athleteActivityResultSplit from AthleteActivityResultSplit athleteActivityResultSplit " +
        "join athleteActivityResultSplit.athleteActivityResult athleteActivityResult " +
        "join athleteActivityResult.athleteActivity athleteActivity " +
        "join athleteActivity.athleteWorkout athleteWorkout " +
        "join athleteWorkout.athleteEvent athleteEvent " +
        "where athleteActivityResultSplit.activityResultSplit.id = :activityResultSplitId and athleteEvent.event.id = :eventId")
    List<AthleteActivityResultSplit> findActivityResultSplitsByEventId(@Param("activityResultSplitId") Long activityResultSplitId, @Param("eventId") Long eventId);
}
