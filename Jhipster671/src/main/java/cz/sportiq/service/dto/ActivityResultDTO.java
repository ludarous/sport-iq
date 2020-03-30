package cz.sportiq.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import cz.sportiq.domain.enumeration.ResultType;

/**
 * A DTO for the ActivityResult entity.
 */
public class ActivityResultDTO implements Serializable {

    private Long id;

    private String name;

    private ResultType resultType;

    private Float ratingWeight;

    private Long activityId;

    private UnitDTO resultUnit;

    private Boolean mainResult;

    private Set<ActivityResultSplitDTO> resultSplits = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public Float getRatingWeight() {
        return ratingWeight;
    }

    public void setRatingWeight(Float ratingWeight) {
        this.ratingWeight = ratingWeight;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public UnitDTO getResultUnit() {
        return resultUnit;
    }

    public void setResultUnit(UnitDTO resultUnit) {
        this.resultUnit = resultUnit;
    }

    public Boolean getMainResult() { return mainResult; }

    public void setMainResult(Boolean mainResult) { this.mainResult = mainResult; }

    public Set<ActivityResultSplitDTO> getResultSplits() {
        return resultSplits;
    }

    public void setResultSplits(Set<ActivityResultSplitDTO> resultSplits) {
        this.resultSplits = resultSplits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActivityResultDTO activityResultDTO = (ActivityResultDTO) o;
        if (activityResultDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activityResultDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActivityResultDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", resultType='" + getResultType() + "'" +
            ", ratingWeight=" + getRatingWeight() +
            ", activity=" + getActivityId() +
            "}";
    }

}
