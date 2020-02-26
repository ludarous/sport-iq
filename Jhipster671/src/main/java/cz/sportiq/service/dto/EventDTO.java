package cz.sportiq.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link cz.sportiq.domain.Event} entity.
 */
public class EventDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private ZonedDateTime date;


    private Long eventLocationId;

    private String eventLocationName;

    private Set<WorkoutDTO> tests = new HashSet<>();

    private Set<AthleteDTO> athletes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Long getEventLocationId() {
        return eventLocationId;
    }

    public void setEventLocationId(Long eventLocationId) {
        this.eventLocationId = eventLocationId;
    }

    public String getEventLocationName() {
        return eventLocationName;
    }

    public void setEventLocationName(String eventLocationName) {
        this.eventLocationName = eventLocationName;
    }

    public Set<WorkoutDTO> getTests() {
        return tests;
    }

    public void setTests(Set<WorkoutDTO> workouts) {
        this.tests = workouts;
    }

    public Set<AthleteDTO> getAthletes() {
        return athletes;
    }

    public void setAthletes(Set<AthleteDTO> athletes) {
        this.athletes = athletes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventDTO eventDTO = (EventDTO) o;
        if (eventDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            ", eventLocationId=" + getEventLocationId() +
            ", eventLocationName='" + getEventLocationName() + "'" +
            "}";
    }
}
