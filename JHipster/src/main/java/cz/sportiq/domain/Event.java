package cz.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AthleteEvent> athleteEvents = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private Address address;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "event_tests",
               joinColumns = @JoinColumn(name = "events_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tests_id", referencedColumnName = "id"))
    private Set<Workout> tests = new HashSet<>();

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

    public Set<AthleteEvent> getAthleteEvents() {
        return athleteEvents;
    }

    public Event athleteEvents(Set<AthleteEvent> athleteEvents) {
        this.athleteEvents = athleteEvents;
        return this;
    }

    public Event addAthleteEvents(AthleteEvent athleteEvent) {
        this.athleteEvents.add(athleteEvent);
        athleteEvent.setEvent(this);
        return this;
    }

    public Event removeAthleteEvents(AthleteEvent athleteEvent) {
        this.athleteEvents.remove(athleteEvent);
        athleteEvent.setEvent(null);
        return this;
    }

    public void setAthleteEvents(Set<AthleteEvent> athleteEvents) {
        this.athleteEvents = athleteEvents;
    }

    public Address getAddress() {
        return address;
    }

    public Event address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Workout> getTests() {
        return tests;
    }

    public Event tests(Set<Workout> workouts) {
        this.tests = workouts;
        return this;
    }

    public Event addTests(Workout workout) {
        this.tests.add(workout);
        return this;
    }

    public Event removeTests(Workout workout) {
        this.tests.remove(workout);
        return this;
    }

    public void setTests(Set<Workout> workouts) {
        this.tests = workouts;
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
        Event event = (Event) o;
        if (event.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
