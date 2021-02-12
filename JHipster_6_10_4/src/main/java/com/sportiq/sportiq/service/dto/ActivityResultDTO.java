package com.sportiq.sportiq.service.dto;

import java.io.Serializable;
import com.sportiq.sportiq.domain.enumeration.ResultType;

/**
 * A DTO for the {@link com.sportiq.sportiq.domain.ActivityResult} entity.
 */
public class ActivityResultDTO implements Serializable {
    
    private Long id;

    private String name;

    private ResultType resultType;

    private Float ratingWeight;

    private Boolean mainResult;

    private Integer order;

    private Float irvBest;

    private Float irvWorst;


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

    public Boolean isMainResult() {
        return mainResult;
    }

    public void setMainResult(Boolean mainResult) {
        this.mainResult = mainResult;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Float getIrvBest() {
        return irvBest;
    }

    public void setIrvBest(Float irvBest) {
        this.irvBest = irvBest;
    }

    public Float getIrvWorst() {
        return irvWorst;
    }

    public void setIrvWorst(Float irvWorst) {
        this.irvWorst = irvWorst;
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
        if (!(o instanceof ActivityResultDTO)) {
            return false;
        }

        return id != null && id.equals(((ActivityResultDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivityResultDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", resultType='" + getResultType() + "'" +
            ", ratingWeight=" + getRatingWeight() +
            ", mainResult='" + isMainResult() + "'" +
            ", order=" + getOrder() +
            ", irvBest=" + getIrvBest() +
            ", irvWorst=" + getIrvWorst() +
            ", resultUnitId=" + getResultUnitId() +
            ", resultUnitName='" + getResultUnitName() + "'" +
            ", activityId=" + getActivityId() +
            "}";
    }
}
