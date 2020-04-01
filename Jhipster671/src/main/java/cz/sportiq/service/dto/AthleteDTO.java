package cz.sportiq.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import cz.sportiq.domain.enumeration.Sex;
import cz.sportiq.domain.enumeration.Laterality;

/**
 * A DTO for the {@link cz.sportiq.domain.Athlete} entity.
 */
public class AthleteDTO implements Serializable {

    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    private String phone;

    private ZonedDateTime birthDate;

    private String nationality;

    private Sex sex;

    private String country;

    private String city;

    private String street;

    private String zipCode;

    private Laterality handLaterality;

    private Laterality footLaterality;

    private Laterality steppingFoot;

    private Boolean termsAgreement;

    private Boolean gdprAgreement;

    private Boolean photographyAgreement;

    private Boolean medicalFitnessAgreement;

    private Boolean marketingAgreement;

    private String lrFirstName;

    private String lrLastName;

    private String lrEmail;

    private String lrPhone;

    private Boolean profileCompleted;


    private String userId;

    private Set<SportDTO> sports = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ZonedDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(ZonedDateTime birthDate) {
        this.birthDate = birthDate;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Laterality getHandLaterality() {
        return handLaterality;
    }

    public void setHandLaterality(Laterality handLaterality) {
        this.handLaterality = handLaterality;
    }

    public Laterality getFootLaterality() {
        return footLaterality;
    }

    public void setFootLaterality(Laterality footLaterality) {
        this.footLaterality = footLaterality;
    }

    public Laterality getSteppingFoot() {
        return steppingFoot;
    }

    public void setSteppingFoot(Laterality steppingFoot) {
        this.steppingFoot = steppingFoot;
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

    public Boolean isMedicalFitnessAgreement() {
        return medicalFitnessAgreement;
    }

    public void setMedicalFitnessAgreement(Boolean medicalFitnessAgreement) {
        this.medicalFitnessAgreement = medicalFitnessAgreement;
    }

    public Boolean isMarketingAgreement() {
        return marketingAgreement;
    }

    public void setMarketingAgreement(Boolean marketingAgreement) {
        this.marketingAgreement = marketingAgreement;
    }

    public String getLrFirstName() {
        return lrFirstName;
    }

    public void setLrFirstName(String lrFirstName) {
        this.lrFirstName = lrFirstName;
    }

    public String getLrLastName() {
        return lrLastName;
    }

    public void setLrLastName(String lrLastName) {
        this.lrLastName = lrLastName;
    }

    public String getLrEmail() {
        return lrEmail;
    }

    public void setLrEmail(String lrEmail) {
        this.lrEmail = lrEmail;
    }

    public String getLrPhone() {
        return lrPhone;
    }

    public void setLrPhone(String lrPhone) {
        this.lrPhone = lrPhone;
    }

    public Boolean isProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(Boolean profileCompleted) {
        this.profileCompleted = profileCompleted;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<SportDTO> getSports() {
        return sports;
    }

    public void setSports(Set<SportDTO> sports) {
        this.sports = sports;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AthleteDTO athleteDTO = (AthleteDTO) o;
        if (athleteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), athleteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AthleteDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", sex='" + getSex() + "'" +
            ", country='" + getCountry() + "'" +
            ", city='" + getCity() + "'" +
            ", street='" + getStreet() + "'" +
            ", zipCode='" + getZipCode() + "'" +
            ", handLaterality='" + getHandLaterality() + "'" +
            ", footLaterality='" + getFootLaterality() + "'" +
            ", steppingFoot='" + getSteppingFoot() + "'" +
            ", termsAgreement='" + isTermsAgreement() + "'" +
            ", gdprAgreement='" + isGdprAgreement() + "'" +
            ", photographyAgreement='" + isPhotographyAgreement() + "'" +
            ", medicalFitnessAgreement='" + isMedicalFitnessAgreement() + "'" +
            ", marketingAgreement='" + isMarketingAgreement() + "'" +
            ", lrFirstName='" + getLrFirstName() + "'" +
            ", lrLastName='" + getLrLastName() + "'" +
            ", lrEmail='" + getLrEmail() + "'" +
            ", lrPhone='" + getLrPhone() + "'" +
            ", profileCompleted='" + isProfileCompleted() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }
}
