package com.sportiq.sportiq.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.sportiq.sportiq.domain.EventLocation} entity.
 */
public class EventLocationDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String state;

    private String city;

    private String street;

    private String streetNumber;

    private Float latitude;

    private Float longitude;

    private Integer capacity;

    private String mapLink;

    
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getMapLink() {
        return mapLink;
    }

    public void setMapLink(String mapLink) {
        this.mapLink = mapLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventLocationDTO)) {
            return false;
        }

        return id != null && id.equals(((EventLocationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventLocationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", state='" + getState() + "'" +
            ", city='" + getCity() + "'" +
            ", street='" + getStreet() + "'" +
            ", streetNumber='" + getStreetNumber() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", capacity=" + getCapacity() +
            ", mapLink='" + getMapLink() + "'" +
            "}";
    }
}
