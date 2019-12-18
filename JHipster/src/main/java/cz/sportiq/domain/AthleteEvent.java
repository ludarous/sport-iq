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
 * A AthleteEvent.
 */
@Entity
@Table(name = "athlete_event", uniqueConstraints={@UniqueConstraint(columnNames = {"event_id", "athlete_id"})})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "athleteevent")
public class AthleteEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "note", length = 65535)
    private String note;

    @Column(name = "actual_height_in_cm")
    private Float actualHeightInCm;

    @Column(name = "actual_weight_in_kg")
    private Float actualWeightInKg;

    @ManyToOne
    @JsonIgnoreProperties("athleteEvents")
    private Event event;

    @OneToMany(mappedBy = "athleteEvent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AthleteWorkout> athleteWorkouts = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Athlete athlete;

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

    public AthleteEvent note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Float getActualHeightInCm() {
        return actualHeightInCm;
    }

    public AthleteEvent actualHeightInCm(Float actualHeightInCm) {
        this.actualHeightInCm = actualHeightInCm;
        return this;
    }

    public void setActualHeightInCm(Float actualHeightInCm) {
        this.actualHeightInCm = actualHeightInCm;
    }

    public Float getActualWeightInKg() {
        return actualWeightInKg;
    }

    public AthleteEvent actualWeightInKg(Float actualWeightInKg) {
        this.actualWeightInKg = actualWeightInKg;
        return this;
    }

    public void setActualWeightInKg(Float actualWeightInKg) {
        this.actualWeightInKg = actualWeightInKg;
    }

    public Event getEvent() {
        return event;
    }

    public AthleteEvent event(Event event) {
        this.event = event;
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Set<AthleteWorkout> getAthleteWorkouts() {
        return athleteWorkouts;
    }

    public AthleteEvent athleteWorkouts(Set<AthleteWorkout> athleteWorkouts) {
        this.athleteWorkouts = athleteWorkouts;
        return this;
    }

    public AthleteEvent addAthleteWorkouts(AthleteWorkout athleteWorkout) {
        this.athleteWorkouts.add(athleteWorkout);
        athleteWorkout.setAthleteEvent(this);
        return this;
    }

    public AthleteEvent removeAthleteWorkouts(AthleteWorkout athleteWorkout) {
        this.athleteWorkouts.remove(athleteWorkout);
        athleteWorkout.setAthleteEvent(null);
        return this;
    }

    public void setAthleteWorkouts(Set<AthleteWorkout> athleteWorkouts) {
        this.athleteWorkouts = athleteWorkouts;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public AthleteEvent athlete(Athlete athlete) {
        this.athlete = athlete;
        return this;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
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
        AthleteEvent athleteEvent = (AthleteEvent) o;
        if (athleteEvent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), athleteEvent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AthleteEvent{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            ", actualHeightInCm=" + getActualHeightInCm() +
            ", actualWeightInKg=" + getActualWeightInKg() +
            "}";
    }
}
