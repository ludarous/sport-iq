package com.sportiq.sportiq.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

import com.sportiq.sportiq.domain.enumeration.Sex;

/**
 * A UserProperties.
 */
@Entity
@Table(name = "user_properties")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "birth_date")
    private ZonedDateTime birthDate;

    @Column(name = "phone")
    private String phone;

    @Column(name = "nationality")
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getBirthDate() {
        return birthDate;
    }

    public UserProperties birthDate(ZonedDateTime birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(ZonedDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public UserProperties phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNationality() {
        return nationality;
    }

    public UserProperties nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Sex getSex() {
        return sex;
    }

    public UserProperties sex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserProperties)) {
            return false;
        }
        return id != null && id.equals(((UserProperties) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserProperties{" +
            "id=" + getId() +
            ", birthDate='" + getBirthDate() + "'" +
            ", phone='" + getPhone() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", sex='" + getSex() + "'" +
            "}";
    }
}
