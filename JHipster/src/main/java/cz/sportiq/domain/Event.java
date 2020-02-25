package cz.sportiq.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date")
    private ZonedDateTime date;

    @OneToMany(mappedBy = "event")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AthleteEvent> athleteEvents = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("events")
    private EventLocation eventLocation;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "event_tests",
               joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tests_id", referencedColumnName = "id"))
    private Set<Workout> tests = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "event_athletes",
               joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "athletes_id", referencedColumnName = "id"))
    private Set<Athlete> athletes = new HashSet<>();

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

    public Set<Workout> getTests() {
        return tests;
    }

    public Event tests(Set<Workout> workouts) {
        this.tests = workouts;
        return this;
    }

    public Event addTests(Workout workout) {
        this.tests.add(workout);
        workout.getEvents().add(this);
        return this;
    }

    public Event removeTests(Workout workout) {
        this.tests.remove(workout);
        workout.getEvents().remove(this);
        return this;
    }

    public void setTests(Set<Workout> workouts) {
        this.tests = workouts;
    }

    public Set<Athlete> getAthletes() {
        return athletes;
    }

    public Event athletes(Set<Athlete> athletes) {
        this.athletes = athletes;
        return this;
    }

    public Event addAthletes(Athlete athlete) {
        this.athletes.add(athlete);
        athlete.getEvents().add(this);
        return this;
    }

    public Event removeAthletes(Athlete athlete) {
        this.athletes.remove(athlete);
        athlete.getEvents().remove(this);
        return this;
    }

    public void setAthletes(Set<Athlete> athletes) {
        this.athletes = athletes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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

    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
