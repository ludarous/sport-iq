package cz.sportiq.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link cz.sportiq.domain.AthleteEvent} entity.
 */
public class AthleteEventDTO implements Serializable {

    private Long id;

    private String note;

    private Float actualHeightInCm;

    private Float actualWeightInKg;


    private Long athleteId;

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

    public Float getActualHeightInCm() {
        return actualHeightInCm;
    }

    public void setActualHeightInCm(Float actualHeightInCm) {
        this.actualHeightInCm = actualHeightInCm;
    }

    public Float getActualWeightInKg() {
        return actualWeightInKg;
    }

    public void setActualWeightInKg(Float actualWeightInKg) {
        this.actualWeightInKg = actualWeightInKg;
    }

    public Long getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(Long athleteId) {
        this.athleteId = athleteId;
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AthleteEventDTO athleteEventDTO = (AthleteEventDTO) o;
        if (athleteEventDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), athleteEventDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AthleteEventDTO{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            ", actualHeightInCm=" + getActualHeightInCm() +
            ", actualWeightInKg=" + getActualWeightInKg() +
            ", athleteId=" + getAthleteId() +
            ", eventId=" + getEventId() +
            "}";
    }
}
