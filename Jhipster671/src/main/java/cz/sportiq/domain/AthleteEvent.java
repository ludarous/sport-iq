package cz.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A AthleteEvent.
 */
@Entity
@Table(name = "athlete_event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AthleteEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "note")
    private String note;

    @Column(name = "actual_height_in_cm")
    private Float actualHeightInCm;

    @Column(name = "actual_weight_in_kg")
    private Float actualWeightInKg;

    @Column(name = "medical_fitness_agreement")
    private Boolean medicalFitnessAgreement;

    @Column(name = "registration_date")
    private ZonedDateTime registrationDate;

    @OneToMany(mappedBy = "athleteEvent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AthleteWorkout> athleteWorkouts = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("athleteEvents")
    private Athlete athlete;

    @ManyToOne
    @JsonIgnoreProperties("athleteEvents")
    private Event event;

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

    public Boolean isMedicalFitnessAgreement() {
        return medicalFitnessAgreement;
    }

    public AthleteEvent medicalFitnessAgreement(Boolean medicalFitnessAgreement) {
        this.medicalFitnessAgreement = medicalFitnessAgreement;
        return this;
    }

    public void setMedicalFitnessAgreement(Boolean medicalFitnessAgreement) {
        this.medicalFitnessAgreement = medicalFitnessAgreement;
    }

    public ZonedDateTime getRegistrationDate() {
        return registrationDate;
    }

    public AthleteEvent registrationDate(ZonedDateTime registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public void setRegistrationDate(ZonedDateTime registrationDate) {
        this.registrationDate = registrationDate;
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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AthleteEvent)) {
            return false;
        }
        return id != null && id.equals(((AthleteEvent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AthleteEvent{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            ", actualHeightInCm=" + getActualHeightInCm() +
            ", actualWeightInKg=" + getActualWeightInKg() +
            ", medicalFitnessAgreement='" + isMedicalFitnessAgreement() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            "}";
    }
}
