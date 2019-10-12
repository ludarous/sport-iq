package cz.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AthleteActivityResultSplit.
 */
@Entity
@Table(name = "athlete_activity_result_split")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "athleteactivityresultsplit")
public class AthleteActivityResultSplit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_value")
    private Float value;

    @ManyToOne
    @JsonIgnoreProperties("athleteActivityResultSplits")
    private AthleteActivityResult athleteActivityResult;

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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AthleteActivityResultSplit athleteActivityResultSplit = (AthleteActivityResultSplit) o;
        if (athleteActivityResultSplit.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), athleteActivityResultSplit.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AthleteActivityResultSplit{" +
            "id=" + getId() +
            ", value=" + getValue() +
            "}";
    }
}
