package com.sportiq.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A UserActivity.
 */
@Entity
@Table(name = "user_activity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "note")
    private String note;

    @Column(name = "date")
    private ZonedDateTime date;

    @OneToMany(mappedBy = "userActivity")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<UserActivityResult> athleteActivityResults = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "userActivities", allowSetters = true)
    private Activity activity;

    @ManyToOne
    @JsonIgnoreProperties(value = "athleteActivities", allowSetters = true)
    private UserEvent userEvent;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public UserActivity note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public UserActivity date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Set<UserActivityResult> getAthleteActivityResults() {
        return athleteActivityResults;
    }

    public UserActivity athleteActivityResults(Set<UserActivityResult> userActivityResults) {
        this.athleteActivityResults = userActivityResults;
        return this;
    }

    public UserActivity addAthleteActivityResults(UserActivityResult userActivityResult) {
        this.athleteActivityResults.add(userActivityResult);
        userActivityResult.setUserActivity(this);
        return this;
    }

    public UserActivity removeAthleteActivityResults(UserActivityResult userActivityResult) {
        this.athleteActivityResults.remove(userActivityResult);
        userActivityResult.setUserActivity(null);
        return this;
    }

    public void setAthleteActivityResults(Set<UserActivityResult> userActivityResults) {
        this.athleteActivityResults = userActivityResults;
    }

    public Activity getActivity() {
        return activity;
    }

    public UserActivity activity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public UserEvent getUserEvent() {
        return userEvent;
    }

    public UserActivity userEvent(UserEvent userEvent) {
        this.userEvent = userEvent;
        return this;
    }

    public void setUserEvent(UserEvent userEvent) {
        this.userEvent = userEvent;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserActivity)) {
            return false;
        }
        return id != null && id.equals(((UserActivity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserActivity{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
