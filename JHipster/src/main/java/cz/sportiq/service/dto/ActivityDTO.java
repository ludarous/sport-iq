package cz.sportiq.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Activity entity.
 */
public class ActivityDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private String help;

    private Integer minAge;

    private Integer maxAge;

    private Float targetValue;

    private Long targetUnitId;

    private String targetUnitName;

    private Set<ActivityCategoryDTO> categories = new HashSet<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Float getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(Float targetValue) {
        this.targetValue = targetValue;
    }

    public Long getTargetUnitId() {
        return targetUnitId;
    }

    public void setTargetUnitId(Long unitId) {
        this.targetUnitId = unitId;
    }

    public String getTargetUnitName() {
        return targetUnitName;
    }

    public void setTargetUnitName(String unitName) {
        this.targetUnitName = unitName;
    }

    public Set<ActivityCategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<ActivityCategoryDTO> activityCategories) {
        this.categories = activityCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActivityDTO activityDTO = (ActivityDTO) o;
        if (activityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActivityDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", help='" + getHelp() + "'" +
            ", minAge=" + getMinAge() +
            ", maxAge=" + getMaxAge() +
            ", targetValue=" + getTargetValue() +
            ", targetUnit=" + getTargetUnitId() +
            ", targetUnit='" + getTargetUnitName() + "'" +
            "}";
    }
}
