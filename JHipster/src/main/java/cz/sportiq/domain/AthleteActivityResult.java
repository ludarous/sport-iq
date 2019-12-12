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
 * A AthleteActivityResult.
 */
@Entity
@Table(name = "athlete_activity_result", uniqueConstraints={@UniqueConstraint(columnNames = {"activity_result_id", "athlete_activity_id"})})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "athleteactivityresult")
public class AthleteActivityResult implements Serializable, ResultValueable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_value")
    private Float value;

    @Column(name = "compare_value")
    private Float compareValue;

    @ManyToOne
    @JsonIgnoreProperties("athleteActivityResults")
    private AthleteActivity athleteActivity;

    @OneToMany(mappedBy = "athleteActivityResult", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AthleteActivityResultSplit> athleteActivityResultSplits = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private ActivityResult activityResult;

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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AthleteActivityResult athleteActivityResult = (AthleteActivityResult) o;
        if (athleteActivityResult.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), athleteActivityResult.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
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
