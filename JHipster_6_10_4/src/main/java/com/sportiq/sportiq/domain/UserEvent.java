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
 * A UserEvent.
 */
@Entity
@Table(name = "user_event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "note")
    private String note;

    @Column(name = "registration_date")
    private ZonedDateTime registrationDate;

    @OneToMany(mappedBy = "userEvent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<UserActivity> athleteActivities = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "userEvents", allowSetters = true)
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "userEvents", allowSetters = true)
    private Event event;

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

    public UserEvent note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ZonedDateTime getRegistrationDate() {
        return registrationDate;
    }

    public UserEvent registrationDate(ZonedDateTime registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public void setRegistrationDate(ZonedDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Set<UserActivity> getAthleteActivities() {
        return athleteActivities;
    }

    public UserEvent athleteActivities(Set<UserActivity> userActivities) {
        this.athleteActivities = userActivities;
        return this;
    }

    public UserEvent addAthleteActivities(UserActivity userActivity) {
        this.athleteActivities.add(userActivity);
        userActivity.setUserEvent(this);
        return this;
    }

    public UserEvent removeAthleteActivities(UserActivity userActivity) {
        this.athleteActivities.remove(userActivity);
        userActivity.setUserEvent(null);
        return this;
    }

    public void setAthleteActivities(Set<UserActivity> userActivities) {
        this.athleteActivities = userActivities;
    }

    public User getUser() {
        return user;
    }

    public UserEvent user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public UserEvent event(Event event) {
        this.event = event;
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserEvent)) {
            return false;
        }
        return id != null && id.equals(((UserEvent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserEvent{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            "}";
    }
}
