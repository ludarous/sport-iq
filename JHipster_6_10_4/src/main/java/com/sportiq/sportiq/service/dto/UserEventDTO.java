package com.sportiq.sportiq.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.sportiq.sportiq.domain.UserEvent} entity.
 */
public class UserEventDTO implements Serializable {
    
    private Long id;

    private String note;

    private ZonedDateTime registrationDate;


    private String userId;

    private Long eventId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ZonedDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(ZonedDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserEventDTO)) {
            return false;
        }

        return id != null && id.equals(((UserEventDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserEventDTO{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            ", userId='" + getUserId() + "'" +
            ", eventId=" + getEventId() +
            "}";
    }
}
