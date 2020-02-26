package cz.sportiq.service.dto;

import java.io.Serializable;
import java.util.Objects;
import cz.sportiq.domain.enumeration.ResultType;

/**
 * A DTO for the {@link cz.sportiq.domain.ActivityResult} entity.
 */
public class ActivityResultDTO implements Serializable {

    private Long id;

    private String name;

    private ResultType resultType;

    private Float ratingWeight;


    private Long resultUnitId;

    private String resultUnitName;

    private Long activityId;

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

    public Long getResultUnitId() {
        return resultUnitId;
    }

    public void setResultUnitId(Long unitId) {
        this.resultUnitId = unitId;
    }

    public String getResultUnitName() {
        return resultUnitName;
    }

    public void setResultUnitName(String unitName) {
        this.resultUnitName = unitName;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
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
            ", resultUnitId=" + getResultUnitId() +
            ", resultUnitName='" + getResultUnitName() + "'" +
            ", activityId=" + getActivityId() +
            "}";
    }
}
