package com.sportiq.sportiq.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Agreement.
 */
@Entity
@Table(name = "agreement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Agreement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "terms_agreement")
    private Boolean termsAgreement;

    @Column(name = "gdpr_agreement")
    private Boolean gdprAgreement;

    @Column(name = "photography_agreement")
    private Boolean photographyAgreement;

    @Column(name = "marketing_agreement")
    private Boolean marketingAgreement;

    @Column(name = "medical_fitness_agreement")
    private Boolean medicalFitnessAgreement;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isTermsAgreement() {
        return termsAgreement;
    }

    public Agreement termsAgreement(Boolean termsAgreement) {
        this.termsAgreement = termsAgreement;
        return this;
    }

    public void setTermsAgreement(Boolean termsAgreement) {
        this.termsAgreement = termsAgreement;
    }

    public Boolean isGdprAgreement() {
        return gdprAgreement;
    }

    public Agreement gdprAgreement(Boolean gdprAgreement) {
        this.gdprAgreement = gdprAgreement;
        return this;
    }

    public void setGdprAgreement(Boolean gdprAgreement) {
        this.gdprAgreement = gdprAgreement;
    }

    public Boolean isPhotographyAgreement() {
        return photographyAgreement;
    }

    public Agreement photographyAgreement(Boolean photographyAgreement) {
        this.photographyAgreement = photographyAgreement;
        return this;
    }

    public void setPhotographyAgreement(Boolean photographyAgreement) {
        this.photographyAgreement = photographyAgreement;
    }

    public Boolean isMarketingAgreement() {
        return marketingAgreement;
    }

    public Agreement marketingAgreement(Boolean marketingAgreement) {
        this.marketingAgreement = marketingAgreement;
        return this;
    }

    public void setMarketingAgreement(Boolean marketingAgreement) {
        this.marketingAgreement = marketingAgreement;
    }

    public Boolean isMedicalFitnessAgreement() {
        return medicalFitnessAgreement;
    }

    public Agreement medicalFitnessAgreement(Boolean medicalFitnessAgreement) {
        this.medicalFitnessAgreement = medicalFitnessAgreement;
        return this;
    }

    public void setMedicalFitnessAgreement(Boolean medicalFitnessAgreement) {
        this.medicalFitnessAgreement = medicalFitnessAgreement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agreement)) {
            return false;
        }
        return id != null && id.equals(((Agreement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agreement{" +
            "id=" + getId() +
            ", termsAgreement='" + isTermsAgreement() + "'" +
            ", gdprAgreement='" + isGdprAgreement() + "'" +
            ", photographyAgreement='" + isPhotographyAgreement() + "'" +
            ", marketingAgreement='" + isMarketingAgreement() + "'" +
            ", medicalFitnessAgreement='" + isMedicalFitnessAgreement() + "'" +
            "}";
    }
}
