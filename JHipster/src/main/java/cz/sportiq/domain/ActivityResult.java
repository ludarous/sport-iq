package cz.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import cz.sportiq.domain.enumeration.ResultType;

/**
 * A ActivityResult.
 */
@Entity
@Table(name = "activity_result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "activityresult")
public class ActivityResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "result_type")
    private ResultType resultType;

    @Column(name = "rating_weight")
    private Float ratingWeight;

    @ManyToOne
    @JsonIgnoreProperties("activityResults")
    private Activity activity;

    @OneToMany(mappedBy = "activityResult")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ActivityResultSplit> resultSplits = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private Unit resultUnit;

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

    public ActivityResult name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public ActivityResult resultType(ResultType resultType) {
        this.resultType = resultType;
        return this;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public Float getRatingWeight() {
        return ratingWeight;
    }

    public ActivityResult ratingWeight(Float ratingWeight) {
        this.ratingWeight = ratingWeight;
        return this;
    }

    public void setRatingWeight(Float ratingWeight) {
        this.ratingWeight = ratingWeight;
    }

    public Activity getActivity() {
        return activity;
    }

    public ActivityResult activity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Set<ActivityResultSplit> getResultSplits() {
        return resultSplits;
    }

    public ActivityResult resultSplits(Set<ActivityResultSplit> activityResultSplits) {
        this.resultSplits = activityResultSplits;
        return this;
    }

    public ActivityResult addResultSplits(ActivityResultSplit activityResultSplit) {
        this.resultSplits.add(activityResultSplit);
        activityResultSplit.setActivityResult(this);
        return this;
    }

    public ActivityResult removeResultSplits(ActivityResultSplit activityResultSplit) {
        this.resultSplits.remove(activityResultSplit);
        activityResultSplit.setActivityResult(null);
        return this;
    }

    public void setResultSplits(Set<ActivityResultSplit> activityResultSplits) {
        this.resultSplits = activityResultSplits;
    }

    public Unit getResultUnit() {
        return resultUnit;
    }

    public ActivityResult resultUnit(Unit unit) {
        this.resultUnit = unit;
        return this;
    }

    public void setResultUnit(Unit unit) {
        this.resultUnit = unit;
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
        ActivityResult activityResult = (ActivityResult) o;
        if (activityResult.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activityResult.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActivityResult{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", resultType='" + getResultType() + "'" +
            ", ratingWeight=" + getRatingWeight() +
            "}";
    }
}
