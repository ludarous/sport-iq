package cz.sportiq.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Workout.
 */
@Entity
@Table(name = "workout")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "workout")
public class Workout implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 65535)
    private String description;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "workout_activities",
               joinColumns = @JoinColumn(name = "workout_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "activities_id", referencedColumnName = "id"))
    private Set<Activity> activities = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "workout_categories",
               joinColumns = @JoinColumn(name = "workout_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "id"))
    private Set<WorkoutCategory> categories = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "workout_sports",
               joinColumns = @JoinColumn(name = "workout_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "sports_id", referencedColumnName = "id"))
    private Set<Sport> sports = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Workout name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Workout description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public Workout activities(Set<Activity> activities) {
        this.activities = activities;
        return this;
    }

    public Workout addActivities(Activity activity) {
        this.activities.add(activity);
        return this;
    }

    public Workout removeActivities(Activity activity) {
        this.activities.remove(activity);
        return this;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    public Set<WorkoutCategory> getCategories() {
        return categories;
    }

    public Workout categories(Set<WorkoutCategory> workoutCategories) {
        this.categories = workoutCategories;
        return this;
    }

    public Workout addCategories(WorkoutCategory workoutCategory) {
        this.categories.add(workoutCategory);
        return this;
    }

    public Workout removeCategories(WorkoutCategory workoutCategory) {
        this.categories.remove(workoutCategory);
        return this;
    }

    public void setCategories(Set<WorkoutCategory> workoutCategories) {
        this.categories = workoutCategories;
    }

    public Set<Sport> getSports() {
        return sports;
    }

    public Workout sports(Set<Sport> sports) {
        this.sports = sports;
        return this;
    }

    public Workout addSports(Sport sport) {
        this.sports.add(sport);
        return this;
    }

    public Workout removeSports(Sport sport) {
        this.sports.remove(sport);
        return this;
    }

    public void setSports(Set<Sport> sports) {
        this.sports = sports;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Workout)) {
            return false;
        }
        return id != null && id.equals(((Workout) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Workout{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
