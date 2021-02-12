package com.sportiq.sportiq.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.sportiq.sportiq.domain.Agreement} entity.
 */
public class AgreementDTO implements Serializable {
    
    private Long id;

    private Boolean termsAgreement;

    private Boolean gdprAgreement;

    private Boolean photographyAgreement;

    private Boolean marketingAgreement;

    private Boolean medicalFitnessAgreement;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isTermsAgreement() {
        return termsAgreement;
    }

    public void setTermsAgreement(Boolean termsAgreement) {
        this.termsAgreement = termsAgreement;
    }

    public Boolean isGdprAgreement() {
        return gdprAgreement;
    }

    public void setGdprAgreement(Boolean gdprAgreement) {
        this.gdprAgreement = gdprAgreement;
    }

    public Boolean isPhotographyAgreement() {
        return photographyAgreement;
    }

    public void setPhotographyAgreement(Boolean photographyAgreement) {
        this.photographyAgreement = photographyAgreement;
    }

    public Boolean isMarketingAgreement() {
        return marketingAgreement;
    }

    public void setMarketingAgreement(Boolean marketingAgreement) {
        this.marketingAgreement = marketingAgreement;
    }

    public Boolean isMedicalFitnessAgreement() {
        return medicalFitnessAgreement;
    }

    public void setMedicalFitnessAgreement(Boolean medicalFitnessAgreement) {
        this.medicalFitnessAgreement = medicalFitnessAgreement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AgreementDTO)) {
            return false;
        }

        return id != null && id.equals(((AgreementDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AgreementDTO{" +
            "id=" + getId() +
            ", termsAgreement='" + isTermsAgreement() + "'" +
            ", gdprAgreement='" + isGdprAgreement() + "'" +
            ", photographyAgreement='" + isPhotographyAgreement() + "'" +
            ", marketingAgreement='" + isMarketingAgreement() + "'" +
            ", medicalFitnessAgreement='" + isMedicalFitnessAgreement() + "'" +
            "}";
    }
}
