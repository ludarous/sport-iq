package cz.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AthleteWorkout.
 */
@Entity
@Table(name = "athlete_workout", uniqueConstraints={@UniqueConstraint(columnNames = {"workout_id", "athlete_event_id"})})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "athleteworkout")
public class AthleteWorkout implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JsonIgnoreProperties("athleteWorkouts")
    private AthleteEvent athleteEvent;

    @OneToMany(mappedBy = "athleteWorkout")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AthleteActivity> athleteActivities = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Workout workout;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public AthleteWorkout note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public AthleteEvent getAthleteEvent() {
        return athleteEvent;
    }

    public AthleteWorkout athleteEvent(AthleteEvent athleteEvent) {
        this.athleteEvent = athleteEvent;
        return this;
    }

    public void setAthleteEvent(AthleteEvent athleteEvent) {
        this.athleteEvent = athleteEvent;
    }

    public Set<AthleteActivity> getAthleteActivities() {
        return athleteActivities;
    }

    public AthleteWorkout athleteActivities(Set<AthleteActivity> athleteActivities) {
        this.athleteActivities = athleteActivities;
        return this;
    }

    public AthleteWorkout addAthleteActivities(AthleteActivity athleteActivity) {
        this.athleteActivities.add(athleteActivity);
        athleteActivity.setAthleteWorkout(this);
        return this;
    }

    public AthleteWorkout removeAthleteActivities(AthleteActivity athleteActivity) {
        this.athleteActivities.remove(athleteActivity);
        athleteActivity.setAthleteWorkout(null);
        return this;
    }

    public void setAthleteActivities(Set<AthleteActivity> athleteActivities) {
        this.athleteActivities = athleteActivities;
    }

    public Workout getWorkout() {
        return workout;
    }

    public AthleteWorkout workout(Workout workout) {
        this.workout = workout;
        return this;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AthleteWorkout athleteWorkout = (AthleteWorkout) o;
        if (athleteWorkout.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), athleteWorkout.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AthleteWorkout{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            "}";
    }
}
