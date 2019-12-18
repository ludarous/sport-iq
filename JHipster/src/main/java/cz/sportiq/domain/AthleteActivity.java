package cz.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AthleteActivity.
 */
@Entity
@Table(name = "athlete_activity", uniqueConstraints={@UniqueConstraint(columnNames = {"activity_id", "athlete_workout_id"})})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "athleteactivity")
public class AthleteActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "note", length = 65535)
    private String note;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @ManyToOne
    @JsonIgnoreProperties("athleteActivities")
    private AthleteWorkout athleteWorkout;

    @OneToMany(mappedBy = "athleteActivity", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AthleteActivityResult> athleteActivityResults = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Activity activity;

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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AthleteActivity athleteActivity = (AthleteActivity) o;
        if (athleteActivity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), athleteActivity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
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
