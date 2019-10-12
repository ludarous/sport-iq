package cz.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ActivityResultSplit.
 */
@Entity
@Table(name = "activity_result_split")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "activityresultsplit")
public class ActivityResultSplit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "split_value")
    private Float splitValue;

    @ManyToOne
    @JsonIgnoreProperties("resultSplits")
    private ActivityResult activityResult;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Unit splitUnit;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getSplitValue() {
        return splitValue;
    }

    public ActivityResultSplit splitValue(Float splitValue) {
        this.splitValue = splitValue;
        return this;
    }

    public void setSplitValue(Float splitValue) {
        this.splitValue = splitValue;
    }

    public ActivityResult getActivityResult() {
        return activityResult;
    }

    public ActivityResultSplit activityResult(ActivityResult activityResult) {
        this.activityResult = activityResult;
        return this;
    }

    public void setActivityResult(ActivityResult activityResult) {
        this.activityResult = activityResult;
    }

    public Unit getSplitUnit() {
        return splitUnit;
    }

    public ActivityResultSplit splitUnit(Unit unit) {
        this.splitUnit = unit;
        return this;
    }

    public void setSplitUnit(Unit unit) {
        this.splitUnit = unit;
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
        ActivityResultSplit activityResultSplit = (ActivityResultSplit) o;
        if (activityResultSplit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activityResultSplit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActivityResultSplit{" +
            "id=" + getId() +
            ", splitValue=" + getSplitValue() +
            "}";
    }
}
