package cz.sportiq.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link cz.sportiq.domain.AthleteActivityResult} entity.
 */
public class AthleteActivityResultDTO implements Serializable {

    private Long id;

    private Float value;

    private Float compareValue;


    private Long activityResultId;

    private String activityResultName;

    private Long athleteActivityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Float getCompareValue() {
        return compareValue;
    }

    public void setCompareValue(Float compareValue) {
        this.compareValue = compareValue;
    }

    public Long getActivityResultId() {
        return activityResultId;
    }

    public void setActivityResultId(Long activityResultId) {
        this.activityResultId = activityResultId;
    }

    public String getActivityResultName() {
        return activityResultName;
    }

    public void setActivityResultName(String activityResultName) {
        this.activityResultName = activityResultName;
    }

    public Long getAthleteActivityId() {
        return athleteActivityId;
    }

    public void setAthleteActivityId(Long athleteActivityId) {
        this.athleteActivityId = athleteActivityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AthleteActivityResultDTO athleteActivityResultDTO = (AthleteActivityResultDTO) o;
        if (athleteActivityResultDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), athleteActivityResultDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AthleteActivityResultDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", compareValue=" + getCompareValue() +
            ", activityResultId=" + getActivityResultId() +
            ", activityResultName='" + getActivityResultName() + "'" +
            ", athleteActivityId=" + getAthleteActivityId() +
            "}";
    }
}
