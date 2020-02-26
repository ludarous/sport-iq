package cz.sportiq.service.dto.custom;

import cz.sportiq.service.dto.AthleteEventDTO;
import cz.sportiq.service.dto.EventDTO;

import java.util.HashSet;
import java.util.Set;

public class AthleteEventSummaryDTO {
    private AthleteEventDTO athleteEvent;
    private EventDTO event;

    private Set<AthleteWorkoutSummaryDTO> workoutSummaries = new HashSet<AthleteWorkoutSummaryDTO>();

    public AthleteEventDTO getAthleteEvent() {
        return athleteEvent;
    }

    public void setAthleteEvent(AthleteEventDTO athleteEvent) {
        this.athleteEvent = athleteEvent;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public Set<AthleteWorkoutSummaryDTO> getWorkoutSummaries() {
        return workoutSummaries;
    }

    public void setWorkoutSummaries(Set<AthleteWorkoutSummaryDTO> workoutSummaries) {
        this.workoutSummaries = workoutSummaries;
    }
}
