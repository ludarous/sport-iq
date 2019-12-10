package cz.sportiq.service.dto.custom;

import cz.sportiq.service.dto.AthleteWorkoutDTO;
import cz.sportiq.service.dto.WorkoutDTO;

import java.util.Set;

public class AthleteWorkoutSummaryDTO {
    private AthleteWorkoutDTO athleteWorkout;
    private WorkoutDTO workout;

    private Set<AthleteActivitySummaryDTO> activitySummaries;

    public AthleteWorkoutDTO getAthleteWorkout() {
        return athleteWorkout;
    }

    public void setAthleteWorkout(AthleteWorkoutDTO athleteWorkout) {
        this.athleteWorkout = athleteWorkout;
    }

    public WorkoutDTO getWorkout() {
        return workout;
    }

    public void setWorkout(WorkoutDTO workout) {
        this.workout = workout;
    }

    public Set<AthleteActivitySummaryDTO> getActivitySummaries() {
        return activitySummaries;
    }

    public void setActivitySummaries(Set<AthleteActivitySummaryDTO> activitySummaries) {
        this.activitySummaries = activitySummaries;
    }
}
