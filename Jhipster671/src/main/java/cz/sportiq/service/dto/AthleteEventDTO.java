package cz.sportiq.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link cz.sportiq.domain.AthleteEvent} entity.
 */
public class AthleteEventDTO implements Serializable {

    private Long id;

    private String note;

    private Float actualHeightInCm;

    private Float actualWeightInKg;

    private Boolean medicalFitnessAgreement;

    private ZonedDateTime registrationDate;

    private Long athleteId;

    private Long eventId;

    private Set<AthleteWorkoutDTO> athleteWorkouts = new HashSet<AthleteWorkoutDTO>();

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

    public Boolean isMedicalFitnessAgreement() {
        return medicalFitnessAgreement;
    }

    public void setMedicalFitnessAgreement(Boolean medicalFitnessAgreement) {
        this.medicalFitnessAgreement = medicalFitnessAgreement;
    }

    public ZonedDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(ZonedDateTime registrationDate) {
        this.registrationDate = registrationDate;
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

    public Set<AthleteWorkoutDTO> getAthleteWorkouts() {
        return athleteWorkouts;
    }

    public void setAthleteWorkouts(Set<AthleteWorkoutDTO> athleteWorkouts) {
        this.athleteWorkouts = athleteWorkouts;
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
            ", medicalFitnessAgreement='" + isMedicalFitnessAgreement() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            ", athleteId=" + getAthleteId() +
            ", eventId=" + getEventId() +
            "}";
    }


}
