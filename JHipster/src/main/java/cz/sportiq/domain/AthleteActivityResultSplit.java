package cz.sportiq.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A AthleteActivityResultSplit.
 */
@Entity
@Table(name = "athlete_activity_result_split", uniqueConstraints={@UniqueConstraint(columnNames = {"activity_result_split_id", "athlete_activity_result_id"})})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "athleteactivityresultsplit")
public class AthleteActivityResultSplit implements Serializable, ResultValueable  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "value")
    private Float value;

    @Column(name = "compare_value")
    private Float compareValue;

    @ManyToOne
    @JsonIgnoreProperties("athleteActivityResultSplits")
    private AthleteActivityResult athleteActivityResult;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("athleteActivityResultSplits")
    private ActivityResultSplit activityResultSplit;

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

    public AthleteActivityResultSplit value(Float value) {
        this.value = value;
        return this;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Float getCompareValue() {
        return compareValue;
    }

    public AthleteActivityResultSplit compareValue(Float compareValue) {
        this.compareValue = compareValue;
        return this;
    }

    public void setCompareValue(Float compareValue) {
        this.compareValue = compareValue;
    }

    public AthleteActivityResult getAthleteActivityResult() {
        return athleteActivityResult;
    }

    public AthleteActivityResultSplit athleteActivityResult(AthleteActivityResult athleteActivityResult) {
        this.athleteActivityResult = athleteActivityResult;
        return this;
    }

    public void setAthleteActivityResult(AthleteActivityResult athleteActivityResult) {
        this.athleteActivityResult = athleteActivityResult;
    }

    public ActivityResultSplit getActivityResultSplit() {
        return activityResultSplit;
    }

    public AthleteActivityResultSplit activityResultSplit(ActivityResultSplit activityResultSplit) {
        this.activityResultSplit = activityResultSplit;
        return this;
    }

    public void setActivityResultSplit(ActivityResultSplit activityResultSplit) {
        this.activityResultSplit = activityResultSplit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AthleteActivityResultSplit)) {
            return false;
        }
        return id != null && id.equals(((AthleteActivityResultSplit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AthleteActivityResultSplit{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", compareValue=" + getCompareValue() +
            "}";
    }
}
