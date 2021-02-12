package com.sportiq.sportiq.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.sportiq.sportiq.domain.UserActivity} entity.
 */
public class UserActivityDTO implements Serializable {
    
    private Long id;

    private String note;

    private ZonedDateTime date;


    private Long activityId;

    private String activityName;

    private Long userEventId;
    
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

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Long getUserEventId() {
        return userEventId;
    }

    public void setUserEventId(Long userEventId) {
        this.userEventId = userEventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserActivityDTO)) {
            return false;
        }

        return id != null && id.equals(((UserActivityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserActivityDTO{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            ", date='" + getDate() + "'" +
            ", activityId=" + getActivityId() +
            ", activityName='" + getActivityName() + "'" +
            ", userEventId=" + getUserEventId() +
            "}";
    }
}
