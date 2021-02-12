package com.sportiq.sportiq.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import com.sportiq.sportiq.domain.enumeration.Sex;

/**
 * A DTO for the {@link com.sportiq.sportiq.domain.UserProperties} entity.
 */
public class UserPropertiesDTO implements Serializable {
    
    private Long id;

    private ZonedDateTime birthDate;

    private String phone;

    private String nationality;

    private Sex sex;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(ZonedDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserPropertiesDTO)) {
            return false;
        }

        return id != null && id.equals(((UserPropertiesDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserPropertiesDTO{" +
            "id=" + getId() +
            ", birthDate='" + getBirthDate() + "'" +
            ", phone='" + getPhone() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", sex='" + getSex() + "'" +
            "}";
    }
}
