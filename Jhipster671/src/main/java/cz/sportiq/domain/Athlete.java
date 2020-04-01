package cz.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import cz.sportiq.domain.enumeration.Sex;

import cz.sportiq.domain.enumeration.Laterality;

/**
 * A Athlete.
 */
@Entity
@Table(name = "athlete")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Athlete implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birth_date")
    private ZonedDateTime birthDate;

    @Column(name = "nationality")
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "zip_code")
    private String zipCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "hand_laterality")
    private Laterality handLaterality;

    @Enumerated(EnumType.STRING)
    @Column(name = "foot_laterality")
    private Laterality footLaterality;

    @Enumerated(EnumType.STRING)
    @Column(name = "stepping_foot")
    private Laterality steppingFoot;

    @Column(name = "terms_agreement")
    private Boolean termsAgreement;

    @Column(name = "gdpr_agreement")
    private Boolean gdprAgreement;

    @Column(name = "photography_agreement")
    private Boolean photographyAgreement;

    @Column(name = "medical_fitness_agreement")
    private Boolean medicalFitnessAgreement;

    @Column(name = "marketing_agreement")
    private Boolean marketingAgreement;

    @Column(name = "lr_first_name")
    private String lrFirstName;

    @Column(name = "lr_last_name")
    private String lrLastName;

    @Column(name = "lr_email")
    private String lrEmail;

    @Column(name = "lr_phone")
    private String lrPhone;

    @Column(name = "profile_completed")
    private Boolean profileCompleted;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "athlete_sports",
               joinColumns = @JoinColumn(name = "athlete_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "sports_id", referencedColumnName = "id"))
    private Set<Sport> sports = new HashSet<>();

    @ManyToMany(mappedBy = "athletes")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Event> events = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Athlete firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Athlete lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Athlete email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Athlete phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ZonedDateTime getBirthDate() {
        return birthDate;
    }

    public Athlete birthDate(ZonedDateTime birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public void setBirthDate(ZonedDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public Athlete nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Sex getSex() {
        return sex;
    }

    public Athlete sex(Sex sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public Athlete country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public Athlete city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public Athlete street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Athlete zipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Laterality getHandLaterality() {
        return handLaterality;
    }

    public Athlete handLaterality(Laterality handLaterality) {
        this.handLaterality = handLaterality;
        return this;
    }

    public void setHandLaterality(Laterality handLaterality) {
        this.handLaterality = handLaterality;
    }

    public Laterality getFootLaterality() {
        return footLaterality;
    }

    public Athlete footLaterality(Laterality footLaterality) {
        this.footLaterality = footLaterality;
        return this;
    }

    public void setFootLaterality(Laterality footLaterality) {
        this.footLaterality = footLaterality;
    }

    public Laterality getSteppingFoot() {
        return steppingFoot;
    }

    public Athlete steppingFoot(Laterality steppingFoot) {
        this.steppingFoot = steppingFoot;
        return this;
    }

    public void setSteppingFoot(Laterality steppingFoot) {
        this.steppingFoot = steppingFoot;
    }

    public Boolean isTermsAgreement() {
        return termsAgreement;
    }

    public Athlete termsAgreement(Boolean termsAgreement) {
        this.termsAgreement = termsAgreement;
        return this;
    }

    public void setTermsAgreement(Boolean termsAgreement) {
        this.termsAgreement = termsAgreement;
    }

    public Boolean isGdprAgreement() {
        return gdprAgreement;
    }

    public Athlete gdprAgreement(Boolean gdprAgreement) {
        this.gdprAgreement = gdprAgreement;
        return this;
    }

    public void setGdprAgreement(Boolean gdprAgreement) {
        this.gdprAgreement = gdprAgreement;
    }

    public Boolean isPhotographyAgreement() {
        return photographyAgreement;
    }

    public Athlete photographyAgreement(Boolean photographyAgreement) {
        this.photographyAgreement = photographyAgreement;
        return this;
    }

    public void setPhotographyAgreement(Boolean photographyAgreement) {
        this.photographyAgreement = photographyAgreement;
    }

    public Boolean isMedicalFitnessAgreement() {
        return medicalFitnessAgreement;
    }

    public Athlete medicalFitnessAgreement(Boolean medicalFitnessAgreement) {
        this.medicalFitnessAgreement = medicalFitnessAgreement;
        return this;
    }

    public void setMedicalFitnessAgreement(Boolean medicalFitnessAgreement) {
        this.medicalFitnessAgreement = medicalFitnessAgreement;
    }

    public Boolean isMarketingAgreement() {
        return marketingAgreement;
    }

    public Athlete marketingAgreement(Boolean marketingAgreement) {
        this.marketingAgreement = marketingAgreement;
        return this;
    }

    public void setMarketingAgreement(Boolean marketingAgreement) {
        this.marketingAgreement = marketingAgreement;
    }

    public String getLrFirstName() {
        return lrFirstName;
    }

    public Athlete lrFirstName(String lrFirstName) {
        this.lrFirstName = lrFirstName;
        return this;
    }

    public void setLrFirstName(String lrFirstName) {
        this.lrFirstName = lrFirstName;
    }

    public String getLrLastName() {
        return lrLastName;
    }

    public Athlete lrLastName(String lrLastName) {
        this.lrLastName = lrLastName;
        return this;
    }

    public void setLrLastName(String lrLastName) {
        this.lrLastName = lrLastName;
    }

    public String getLrEmail() {
        return lrEmail;
    }

    public Athlete lrEmail(String lrEmail) {
        this.lrEmail = lrEmail;
        return this;
    }

    public void setLrEmail(String lrEmail) {
        this.lrEmail = lrEmail;
    }

    public String getLrPhone() {
        return lrPhone;
    }

    public Athlete lrPhone(String lrPhone) {
        this.lrPhone = lrPhone;
        return this;
    }

    public void setLrPhone(String lrPhone) {
        this.lrPhone = lrPhone;
    }

    public Boolean isProfileCompleted() {
        return profileCompleted;
    }

    public Athlete profileCompleted(Boolean profileCompleted) {
        this.profileCompleted = profileCompleted;
        return this;
    }

    public void setProfileCompleted(Boolean profileCompleted) {
        this.profileCompleted = profileCompleted;
    }

    public User getUser() {
        return user;
    }

    public Athlete user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Sport> getSports() {
        return sports;
    }

    public Athlete sports(Set<Sport> sports) {
        this.sports = sports;
        return this;
    }

    public Athlete addSports(Sport sport) {
        this.sports.add(sport);
        sport.getAthletes().add(this);
        return this;
    }

    public Athlete removeSports(Sport sport) {
        this.sports.remove(sport);
        sport.getAthletes().remove(this);
        return this;
    }

    public void setSports(Set<Sport> sports) {
        this.sports = sports;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public Athlete events(Set<Event> events) {
        this.events = events;
        return this;
    }

    public Athlete addEvents(Event event) {
        this.events.add(event);
        event.getAthletes().add(this);
        return this;
    }

    public Athlete removeEvents(Event event) {
        this.events.remove(event);
        event.getAthletes().remove(this);
        return this;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Athlete)) {
            return false;
        }
        return id != null && id.equals(((Athlete) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Athlete{" +
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
            "}";
    }
}
