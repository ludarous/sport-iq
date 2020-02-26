package cz.sportiq.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link cz.sportiq.domain.ActivityCategory} entity.
 */
public class ActivityCategoryDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;

    private Long parentActivityCategoryId;

    private Set<ActivityCategoryDTO> childActivityCategories;

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

    public Long getParentActivityCategoryId() {
        return parentActivityCategoryId;
    }

    public void setParentActivityCategoryId(Long activityCategoryId) {
        this.parentActivityCategoryId = activityCategoryId;
    }

    public Set<ActivityCategoryDTO> getChildActivityCategories() {
        return childActivityCategories;
    }

    public void setChildActivityCategories(Set<ActivityCategoryDTO> childActivityCategories) {
        this.childActivityCategories = childActivityCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActivityCategoryDTO activityCategoryDTO = (ActivityCategoryDTO) o;
        if (activityCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activityCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActivityCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", parentActivityCategoryId=" + getParentActivityCategoryId() +
            "}";
    }
}
