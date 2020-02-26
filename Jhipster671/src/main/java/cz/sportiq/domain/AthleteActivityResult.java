package cz.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A AthleteActivityResult.
 */
@Entity
@Table(name = "athlete_activity_result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AthleteActivityResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "value")
    private Float value;

    @Column(name = "compare_value")
    private Float compareValue;

    @OneToMany(mappedBy = "athleteActivityResult")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AthleteActivityResultSplit> athleteActivityResultSplits = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("athleteActivityResults")
    private ActivityResult activityResult;

    @ManyToOne
    @JsonIgnoreProperties("athleteActivityResults")
    private AthleteActivity athleteActivity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValue() {
        return value;
    }

    public AthleteActivityResult value(Float value) {
        this.value = value;
        return this;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Float getCompareValue() {
        return compareValue;
    }

    public AthleteActivityResult compareValue(Float compareValue) {
        this.compareValue = compareValue;
        return this;
    }

    public void setCompareValue(Float compareValue) {
        this.compareValue = compareValue;
    }

    public Set<AthleteActivityResultSplit> getAthleteActivityResultSplits() {
        return athleteActivityResultSplits;
    }

    public AthleteActivityResult athleteActivityResultSplits(Set<AthleteActivityResultSplit> athleteActivityResultSplits) {
        this.athleteActivityResultSplits = athleteActivityResultSplits;
        return this;
    }

    public AthleteActivityResult addAthleteActivityResultSplits(AthleteActivityResultSplit athleteActivityResultSplit) {
        this.athleteActivityResultSplits.add(athleteActivityResultSplit);
        athleteActivityResultSplit.setAthleteActivityResult(this);
        return this;
    }

    public AthleteActivityResult removeAthleteActivityResultSplits(AthleteActivityResultSplit athleteActivityResultSplit) {
        this.athleteActivityResultSplits.remove(athleteActivityResultSplit);
        athleteActivityResultSplit.setAthleteActivityResult(null);
        return this;
    }

    public void setAthleteActivityResultSplits(Set<AthleteActivityResultSplit> athleteActivityResultSplits) {
        this.athleteActivityResultSplits = athleteActivityResultSplits;
    }

    public ActivityResult getActivityResult() {
        return activityResult;
    }

    public AthleteActivityResult activityResult(ActivityResult activityResult) {
        this.activityResult = activityResult;
        return this;
    }

    public void setActivityResult(ActivityResult activityResult) {
        this.activityResult = activityResult;
    }

    public AthleteActivity getAthleteActivity() {
        return athleteActivity;
    }

    public AthleteActivityResult athleteActivity(AthleteActivity athleteActivity) {
        this.athleteActivity = athleteActivity;
        return this;
    }

    public void setAthleteActivity(AthleteActivity athleteActivity) {
        this.athleteActivity = athleteActivity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AthleteActivityResult)) {
            return false;
        }
        return id != null && id.equals(((AthleteActivityResult) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AthleteActivityResult{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", compareValue=" + getCompareValue() +
            "}";
    }
}