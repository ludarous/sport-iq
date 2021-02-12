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
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date")
    private ZonedDateTime date;

    @ManyToOne
    @JsonIgnoreProperties(value = "events", allowSetters = true)
    private EventLocation eventLocation;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "event_activities",
               joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "activities_id", referencedColumnName = "id"))
    private Set<Activity> activities = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "event_entrants",
               joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "entrants_id", referencedColumnName = "id"))
    private Set<User> entrants = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Event name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Event date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public EventLocation getEventLocation() {
        return eventLocation;
    }

    public Event eventLocation(EventLocation eventLocation) {
        this.eventLocation = eventLocation;
        return this;
    }

    public void setEventLocation(EventLocation eventLocation) {
        this.eventLocation = eventLocation;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public Event activities(Set<Activity> activities) {
        this.activities = activities;
        return this;
    }

    public Event addActivities(Activity activity) {
        this.activities.add(activity);
        activity.getEvents().add(this);
        return this;
    }

    public Event removeActivities(Activity activity) {
        this.activities.remove(activity);
        activity.getEvents().remove(this);
        return this;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }

    public Set<User> getEntrants() {
        return entrants;
    }

    public Event entrants(Set<User> users) {
        this.entrants = users;
        return this;
    }

    public Event addEntrants(User user) {
        this.entrants.add(user);
        return this;
    }

    public Event removeEntrants(User user) {
        this.entrants.remove(user);
        return this;
    }

    public void setEntrants(Set<User> users) {
        this.entrants = users;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return id != null && id.equals(((Event) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
