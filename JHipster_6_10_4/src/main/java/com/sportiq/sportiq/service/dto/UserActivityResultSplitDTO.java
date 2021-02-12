package com.sportiq.sportiq.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.sportiq.sportiq.domain.UserActivityResultSplit} entity.
 */
public class UserActivityResultSplitDTO implements Serializable {
    
    private Long id;

    private Float value;

    private Float compareValue;


    private Long activityResultSplitId;

    private Long userActivityResultId;
    
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

    public Long getUserActivityResultId() {
        return userActivityResultId;
    }

    public void setUserActivityResultId(Long userActivityResultId) {
        this.userActivityResultId = userActivityResultId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserActivityResultSplitDTO)) {
            return false;
        }

        return id != null && id.equals(((UserActivityResultSplitDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserActivityResultSplitDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", compareValue=" + getCompareValue() +
            ", activityResultSplitId=" + getActivityResultSplitId() +
            ", userActivityResultId=" + getUserActivityResultId() +
            "}";
    }
}
