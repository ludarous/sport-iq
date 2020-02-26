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
 * A AthleteActivity.
 */
@Entity
@Table(name = "athlete_activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AthleteActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "note")
    private String note;

    @Column(name = "date")
    private ZonedDateTime date;

    @OneToMany(mappedBy = "athleteActivity", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AthleteActivityResult> athleteActivityResults = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("athleteActivities")
    private Activity activity;

    @ManyToOne
    @JsonIgnoreProperties("athleteActivities")
    private AthleteWorkout athleteWorkout;

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

    public AthleteActivity note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public AthleteActivity date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Set<AthleteActivityResult> getAthleteActivityResults() {
        return athleteActivityResults;
    }

    public AthleteActivity athleteActivityResults(Set<AthleteActivityResult> athleteActivityResults) {
        this.athleteActivityResults = athleteActivityResults;
        return this;
    }

    public AthleteActivity addAthleteActivityResults(AthleteActivityResult athleteActivityResult) {
        this.athleteActivityResults.add(athleteActivityResult);
        athleteActivityResult.setAthleteActivity(this);
        return this;
    }

    public AthleteActivity removeAthleteActivityResults(AthleteActivityResult athleteActivityResult) {
        this.athleteActivityResults.remove(athleteActivityResult);
        athleteActivityResult.setAthleteActivity(null);
        return this;
    }

    public void setAthleteActivityResults(Set<AthleteActivityResult> athleteActivityResults) {
        this.athleteActivityResults = athleteActivityResults;
    }

    public Activity getActivity() {
        return activity;
    }

    public AthleteActivity activity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public AthleteWorkout getAthleteWorkout() {
        return athleteWorkout;
    }

    public AthleteActivity athleteWorkout(AthleteWorkout athleteWorkout) {
        this.athleteWorkout = athleteWorkout;
        return this;
    }

    public void setAthleteWorkout(AthleteWorkout athleteWorkout) {
        this.athleteWorkout = athleteWorkout;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AthleteActivity)) {
            return false;
        }
        return id != null && id.equals(((AthleteActivity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AthleteActivity{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
