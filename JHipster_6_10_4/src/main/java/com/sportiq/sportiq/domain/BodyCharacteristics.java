package com.sportiq.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A BodyCharacteristics.
 */
@Entity
@Table(name = "body_characteristics")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BodyCharacteristics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "height")
    private Float height;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "date")
    private ZonedDateTime date;

    @ManyToOne
    @JsonIgnoreProperties(value = "bodyCharacteristics", allowSetters = true)
    private Unit heightUnit;

    @ManyToOne
    @JsonIgnoreProperties(value = "bodyCharacteristics", allowSetters = true)
    private Unit widthUnit;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getHeight() {
        return height;
    }

    public BodyCharacteristics height(Float height) {
        this.height = height;
        return this;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public BodyCharacteristics weight(Float weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public BodyCharacteristics date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Unit getHeightUnit() {
        return heightUnit;
    }

    public BodyCharacteristics heightUnit(Unit unit) {
        this.heightUnit = unit;
        return this;
    }

    public void setHeightUnit(Unit unit) {
        this.heightUnit = unit;
    }

    public Unit getWidthUnit() {
        return widthUnit;
    }

    public BodyCharacteristics widthUnit(Unit unit) {
        this.widthUnit = unit;
        return this;
    }

    public void setWidthUnit(Unit unit) {
        this.widthUnit = unit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BodyCharacteristics)) {
            return false;
        }
        return id != null && id.equals(((BodyCharacteristics) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BodyCharacteristics{" +
            "id=" + getId() +
            ", height=" + getHeight() +
            ", weight=" + getWeight() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
