package com.sportiq.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A UserActivityResultSplit.
 */
@Entity
@Table(name = "user_activity_result_split")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserActivityResultSplit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "value")
    private Float value;

    @Column(name = "compare_value")
    private Float compareValue;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "userActivityResultSplits", allowSetters = true)
    private ActivityResultSplit activityResultSplit;

    @ManyToOne
    @JsonIgnoreProperties(value = "athleteActivityResultSplits", allowSetters = true)
    private UserActivityResult userActivityResult;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValue() {
        return value;
    }

    public UserActivityResultSplit value(Float value) {
        this.value = value;
        return this;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Float getCompareValue() {
        return compareValue;
    }

    public UserActivityResultSplit compareValue(Float compareValue) {
        this.compareValue = compareValue;
        return this;
    }

    public void setCompareValue(Float compareValue) {
        this.compareValue = compareValue;
    }

    public ActivityResultSplit getActivityResultSplit() {
        return activityResultSplit;
    }

    public UserActivityResultSplit activityResultSplit(ActivityResultSplit activityResultSplit) {
        this.activityResultSplit = activityResultSplit;
        return this;
    }

    public void setActivityResultSplit(ActivityResultSplit activityResultSplit) {
        this.activityResultSplit = activityResultSplit;
    }

    public UserActivityResult getUserActivityResult() {
        return userActivityResult;
    }

    public UserActivityResultSplit userActivityResult(UserActivityResult userActivityResult) {
        this.userActivityResult = userActivityResult;
        return this;
    }

    public void setUserActivityResult(UserActivityResult userActivityResult) {
        this.userActivityResult = userActivityResult;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserActivityResultSplit)) {
            return false;
        }
        return id != null && id.equals(((UserActivityResultSplit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserActivityResultSplit{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", compareValue=" + getCompareValue() +
            "}";
    }
}
