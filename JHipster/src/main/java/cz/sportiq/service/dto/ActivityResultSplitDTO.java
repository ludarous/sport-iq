package cz.sportiq.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the ActivityResultSplit entity.
 */
public class ActivityResultSplitDTO implements Serializable {

    private Long id;

    private Float splitValue;

    private Long activityResultId;

    private Long splitUnitId;

    private String splitUnitName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getSplitValue() {
        return splitValue;
    }

    public void setSplitValue(Float splitValue) {
        this.splitValue = splitValue;
    }

    public Long getActivityResultId() {
        return activityResultId;
    }

    public void setActivityResultId(Long activityResultId) {
        this.activityResultId = activityResultId;
    }

    public Long getSplitUnitId() {
        return splitUnitId;
    }

    public void setSplitUnitId(Long unitId) {
        this.splitUnitId = unitId;
    }

    public String getSplitUnitName() {
        return splitUnitName;
    }

    public void setSplitUnitName(String unitName) {
        this.splitUnitName = unitName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActivityResultSplitDTO activityResultSplitDTO = (ActivityResultSplitDTO) o;
        if (activityResultSplitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activityResultSplitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActivityResultSplitDTO{" +
            "id=" + getId() +
            ", splitValue=" + getSplitValue() +
            ", activityResult=" + getActivityResultId() +
            ", splitUnit=" + getSplitUnitId() +
            ", splitUnit='" + getSplitUnitName() + "'" +
            "}";
    }
}
