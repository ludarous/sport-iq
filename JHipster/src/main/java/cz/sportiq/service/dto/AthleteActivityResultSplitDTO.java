package cz.sportiq.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AthleteActivityResultSplit entity.
 */
public class AthleteActivityResultSplitDTO implements Serializable {

    private Long id;

    private Float value;

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
            ", athleteActivityResult=" + getAthleteActivityResultId() +
            "}";
    }
}
