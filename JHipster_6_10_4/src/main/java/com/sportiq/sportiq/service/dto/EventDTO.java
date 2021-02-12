package com.sportiq.sportiq.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.sportiq.sportiq.domain.Event} entity.
 */
public class EventDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private ZonedDateTime date;


    private Long eventLocationId;

    private String eventLocationName;
    private Set<ActivityDTO> activities = new HashSet<>();
    private Set<UserDTO> entrants = new HashSet<>();
    
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

    public Set<ActivityDTO> getActivities() {
        return activities;
    }

    public void setActivities(Set<ActivityDTO> activities) {
        this.activities = activities;
    }

    public Set<UserDTO> getEntrants() {
        return entrants;
    }

    public void setEntrants(Set<UserDTO> users) {
        this.entrants = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventDTO)) {
            return false;
        }

        return id != null && id.equals(((EventDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            ", eventLocationId=" + getEventLocationId() +
            ", eventLocationName='" + getEventLocationName() + "'" +
            ", activities='" + getActivities() + "'" +
            ", entrants='" + getEntrants() + "'" +
            "}";
    }
}
