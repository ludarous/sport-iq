package com.sportiq.sportiq.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.sportiq.sportiq.domain.UserActivityResult} entity.
 */
public class UserActivityResultDTO implements Serializable {
    
    private Long id;

    private Float value;

    private Float compareValue;


    private Long activityResultId;

    private String activityResultName;

    private Long userActivityId;
    
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

    public Long getUserActivityId() {
        return userActivityId;
    }

    public void setUserActivityId(Long userActivityId) {
        this.userActivityId = userActivityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserActivityResultDTO)) {
            return false;
        }

        return id != null && id.equals(((UserActivityResultDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserActivityResultDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", compareValue=" + getCompareValue() +
            ", activityResultId=" + getActivityResultId() +
            ", activityResultName='" + getActivityResultName() + "'" +
            ", userActivityId=" + getUserActivityId() +
            "}";
    }
}
