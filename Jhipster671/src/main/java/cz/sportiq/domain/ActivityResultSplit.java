package cz.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ActivityResultSplit.
 */
@Entity
@Table(name = "activity_result_split")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ActivityResultSplit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "split_value")
    private Float splitValue;

    @ManyToOne
    @JsonIgnoreProperties("activityResultSplits")
    private Unit splitUnit;

    @ManyToOne
    @JsonIgnoreProperties("resultSplits")
    private ActivityResult activityResult;

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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivityResultSplit)) {
            return false;
        }
        return id != null && id.equals(((ActivityResultSplit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ActivityResultSplit{" +
            "id=" + getId() +
            ", splitValue=" + getSplitValue() +
            "}";
    }
}
