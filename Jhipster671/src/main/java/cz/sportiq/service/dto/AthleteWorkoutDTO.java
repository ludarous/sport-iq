package cz.sportiq.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link cz.sportiq.domain.AthleteWorkout} entity.
 */
public class AthleteWorkoutDTO implements Serializable {

    private Long id;

    private String note;


    private Long workoutId;

    private String workoutName;

    private Long athleteEventId;

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

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public Long getAthleteEventId() {
        return athleteEventId;
    }

    public void setAthleteEventId(Long athleteEventId) {
        this.athleteEventId = athleteEventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AthleteWorkoutDTO athleteWorkoutDTO = (AthleteWorkoutDTO) o;
        if (athleteWorkoutDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), athleteWorkoutDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AthleteWorkoutDTO{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            ", workoutId=" + getWorkoutId() +
            ", workoutName='" + getWorkoutName() + "'" +
            ", athleteEventId=" + getAthleteEventId() +
            "}";
    }
}
