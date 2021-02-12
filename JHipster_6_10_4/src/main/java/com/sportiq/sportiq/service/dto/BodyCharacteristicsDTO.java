package com.sportiq.sportiq.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;

/**
 * A DTO for the {@link com.sportiq.sportiq.domain.BodyCharacteristics} entity.
 */
public class BodyCharacteristicsDTO implements Serializable {
    
    private Long id;

    private Float height;

    private Float weight;

    private ZonedDateTime date;


    private Long heightUnitId;

    private Long widthUnitId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Long getHeightUnitId() {
        return heightUnitId;
    }

    public void setHeightUnitId(Long unitId) {
        this.heightUnitId = unitId;
    }

    public Long getWidthUnitId() {
        return widthUnitId;
    }

    public void setWidthUnitId(Long unitId) {
        this.widthUnitId = unitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BodyCharacteristicsDTO)) {
            return false;
        }

        return id != null && id.equals(((BodyCharacteristicsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BodyCharacteristicsDTO{" +
            "id=" + getId() +
            ", height=" + getHeight() +
            ", weight=" + getWeight() +
            ", date='" + getDate() + "'" +
            ", heightUnitId=" + getHeightUnitId() +
            ", widthUnitId=" + getWidthUnitId() +
            "}";
    }
}
