package cz.sportiq.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the AthleteEvent entity.
 */
public class AthleteEventDTO implements Serializable {

    private Long id;

    private String note;

    private Float actualHeightInCm;

    private Float actualWeightInKg;

    private Long eventId;

    private String eventName;

    private Long athleteId;

    private Set<AthleteWorkoutDTO> athleteWorkouts = new HashSet<AthleteWorkoutDTO>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Float getActualHeightInCm() {
        return actualHeightInCm;
    }

    public void setActualHeightInCm(Float actualHeightInCm) {
        this.actualHeightInCm = actualHeightInCm;
    }

    public Float getActualWeightInKg() {
        return actualWeightInKg;
    }

    public void setActualWeightInKg(Float actualWeightInKg) {
        this.actualWeightInKg = actualWeightInKg;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() { return eventName; }

    public void setEventName(String eventName) { this.eventName = eventName; }

    public Long getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(Long athleteId) {
        this.athleteId = athleteId;
    }

    public Set<AthleteWorkoutDTO> getAthleteWorkouts() {
        return athleteWorkouts;
    }

    public void setAthleteWorkouts(Set<AthleteWorkoutDTO> athleteWorkouts) {
        this.athleteWorkouts = athleteWorkouts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AthleteEventDTO athleteEventDTO = (AthleteEventDTO) o;
        if (athleteEventDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), athleteEventDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AthleteEventDTO{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            ", actualHeightInCm=" + getActualHeightInCm() +
            ", actualWeightInKg=" + getActualWeightInKg() +
            ", event=" + getEventId() +
            ", athlete=" + getAthleteId() +
            "}";
    }

}
