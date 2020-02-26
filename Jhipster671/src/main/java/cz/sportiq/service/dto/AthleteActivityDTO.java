package cz.sportiq.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link cz.sportiq.domain.AthleteActivity} entity.
 */
public class AthleteActivityDTO implements Serializable {

    private Long id;

    private String note;

    private ZonedDateTime date;

    private Long athleteWorkoutId;

    private Long activityId;

    private String activityName;

    private Set<AthleteActivityResultDTO> athleteActivityResults = new HashSet<>();

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

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Long getAthleteWorkoutId() {
        return athleteWorkoutId;
    }

    public void setAthleteWorkoutId(Long athleteWorkoutId) {
        this.athleteWorkoutId = athleteWorkoutId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Set<AthleteActivityResultDTO> getAthleteActivityResults() { return athleteActivityResults; }

    public void setAthleteActivityResults(Set<AthleteActivityResultDTO> athleteActivityResults) {
        this.athleteActivityResults = athleteActivityResults;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AthleteActivityDTO athleteActivityDTO = (AthleteActivityDTO) o;
        if (athleteActivityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), athleteActivityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AthleteActivityDTO{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            ", date='" + getDate() + "'" +
            ", athleteWorkout=" + getAthleteWorkoutId() +
            ", activity=" + getActivityId() +
            ", activity='" + getActivityName() + "'" +
            "}";
    }
}
