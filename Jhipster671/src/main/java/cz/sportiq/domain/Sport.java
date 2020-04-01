package cz.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Sport.
 */
@Entity
@Table(name = "sport")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "sports")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Athlete> athletes = new HashSet<>();

    @ManyToMany(mappedBy = "sports")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Workout> workouts = new HashSet<>();

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

    public Sport name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Athlete> getAthletes() {
        return athletes;
    }

    public Sport athletes(Set<Athlete> athletes) {
        this.athletes = athletes;
        return this;
    }

    public Sport addAthletes(Athlete athlete) {
        this.athletes.add(athlete);
        athlete.getSports().add(this);
        return this;
    }

    public Sport removeAthletes(Athlete athlete) {
        this.athletes.remove(athlete);
        athlete.getSports().remove(this);
        return this;
    }

    public void setAthletes(Set<Athlete> athletes) {
        this.athletes = athletes;
    }

    public Set<Workout> getWorkouts() {
        return workouts;
    }

    public Sport workouts(Set<Workout> workouts) {
        this.workouts = workouts;
        return this;
    }

    public Sport addWorkouts(Workout workout) {
        this.workouts.add(workout);
        workout.getSports().add(this);
        return this;
    }

    public Sport removeWorkouts(Workout workout) {
        this.workouts.remove(workout);
        workout.getSports().remove(this);
        return this;
    }

    public void setWorkouts(Set<Workout> workouts) {
        this.workouts = workouts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sport)) {
            return false;
        }
        return id != null && id.equals(((Sport) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Sport{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
