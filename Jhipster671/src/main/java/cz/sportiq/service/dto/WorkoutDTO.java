package cz.sportiq.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link cz.sportiq.domain.Workout} entity.
 */
public class WorkoutDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;


    private Set<ActivityDTO> activities = new HashSet<>();

    private Set<WorkoutCategoryDTO> categories = new HashSet<>();

    private Set<SportDTO> sports = new HashSet<>();

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

    public Set<ActivityDTO> getActivities() {
        return activities;
    }

    public void setActivities(Set<ActivityDTO> activities) {
        this.activities = activities;
    }

    public Set<WorkoutCategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<WorkoutCategoryDTO> workoutCategories) {
        this.categories = workoutCategories;
    }

    public Set<SportDTO> getSports() {
        return sports;
    }

    public void setSports(Set<SportDTO> sports) {
        this.sports = sports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkoutDTO workoutDTO = (WorkoutDTO) o;
        if (workoutDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workoutDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkoutDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
