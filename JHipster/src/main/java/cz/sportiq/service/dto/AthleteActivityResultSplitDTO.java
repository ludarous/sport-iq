package cz.sportiq.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link cz.sportiq.domain.AthleteActivityResultSplit} entity.
 */
public class AthleteActivityResultSplitDTO implements Serializable {

    private Long id;

    private Float value;

    private Float compareValue;


    private Long activityResultSplitId;

    private Long athleteActivityResultId;

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

    public Long getActivityResultSplitId() {
        return activityResultSplitId;
    }

    public void setActivityResultSplitId(Long activityResultSplitId) {
        this.activityResultSplitId = activityResultSplitId;
    }

    public Long getAthleteActivityResultId() {
        return athleteActivityResultId;
    }

    public void setAthleteActivityResultId(Long athleteActivityResultId) {
        this.athleteActivityResultId = athleteActivityResultId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AthleteActivityResultSplitDTO athleteActivityResultSplitDTO = (AthleteActivityResultSplitDTO) o;
        if (athleteActivityResultSplitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), athleteActivityResultSplitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AthleteActivityResultSplitDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", compareValue=" + getCompareValue() +
            ", activityResultSplit=" + getActivityResultSplitId() +
            ", athleteActivityResult=" + getAthleteActivityResultId() +
            "}";
    }
}
