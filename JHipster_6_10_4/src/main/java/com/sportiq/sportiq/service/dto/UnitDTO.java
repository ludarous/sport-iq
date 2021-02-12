package com.sportiq.sportiq.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.sportiq.sportiq.domain.Unit} entity.
 */
public class UnitDTO implements Serializable {
    
    private Long id;

    private String name;

    private String abbreviation;

    
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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnitDTO)) {
            return false;
        }

        return id != null && id.equals(((UnitDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UnitDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", abbreviation='" + getAbbreviation() + "'" +
            "}";
    }
}
