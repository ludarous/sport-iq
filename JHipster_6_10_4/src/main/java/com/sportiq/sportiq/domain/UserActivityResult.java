package com.sportiq.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A UserActivityResult.
 */
@Entity
@Table(name = "user_activity_result")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserActivityResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "value")
    private Float value;

    @Column(name = "compare_value")
    private Float compareValue;

    @OneToMany(mappedBy = "userActivityResult")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<UserActivityResultSplit> athleteActivityResultSplits = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "userActivityResults", allowSetters = true)
    private ActivityResult activityResult;

    @ManyToOne
    @JsonIgnoreProperties(value = "athleteActivityResults", allowSetters = true)
    private UserActivity userActivity;

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

    public UserActivityResult value(Float value) {
        this.value = value;
        return this;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Float getCompareValue() {
        return compareValue;
    }

    public UserActivityResult compareValue(Float compareValue) {
        this.compareValue = compareValue;
        return this;
    }

    public void setCompareValue(Float compareValue) {
        this.compareValue = compareValue;
    }

    public Set<UserActivityResultSplit> getAthleteActivityResultSplits() {
        return athleteActivityResultSplits;
    }

    public UserActivityResult athleteActivityResultSplits(Set<UserActivityResultSplit> userActivityResultSplits) {
        this.athleteActivityResultSplits = userActivityResultSplits;
        return this;
    }

    public UserActivityResult addAthleteActivityResultSplits(UserActivityResultSplit userActivityResultSplit) {
        this.athleteActivityResultSplits.add(userActivityResultSplit);
        userActivityResultSplit.setUserActivityResult(this);
        return this;
    }

    public UserActivityResult removeAthleteActivityResultSplits(UserActivityResultSplit userActivityResultSplit) {
        this.athleteActivityResultSplits.remove(userActivityResultSplit);
        userActivityResultSplit.setUserActivityResult(null);
        return this;
    }

    public void setAthleteActivityResultSplits(Set<UserActivityResultSplit> userActivityResultSplits) {
        this.athleteActivityResultSplits = userActivityResultSplits;
    }

    public ActivityResult getActivityResult() {
        return activityResult;
    }

    public UserActivityResult activityResult(ActivityResult activityResult) {
        this.activityResult = activityResult;
        return this;
    }

    public void setActivityResult(ActivityResult activityResult) {
        this.activityResult = activityResult;
    }

    public UserActivity getUserActivity() {
        return userActivity;
    }

    public UserActivityResult userActivity(UserActivity userActivity) {
        this.userActivity = userActivity;
        return this;
    }

    public void setUserActivity(UserActivity userActivity) {
        this.userActivity = userActivity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserActivityResult)) {
            return false;
        }
        return id != null && id.equals(((UserActivityResult) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserActivityResult{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", compareValue=" + getCompareValue() +
            "}";
    }
}
