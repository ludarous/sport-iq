package com.sportiq.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Organisation.
 */
@Entity
@Table(name = "organisation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Organisation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created")
    private ZonedDateTime created;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "organisation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<OrganisationMembership> memberships = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "organisations", allowSetters = true)
    private User owner;

    @ManyToMany(mappedBy = "visibleForOrganisations")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Activity> visibleActivities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Organisation name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Organisation created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public Organisation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<OrganisationMembership> getMemberships() {
        return memberships;
    }

    public Organisation memberships(Set<OrganisationMembership> organisationMemberships) {
        this.memberships = organisationMemberships;
        return this;
    }

    public Organisation addMemberships(OrganisationMembership organisationMembership) {
        this.memberships.add(organisationMembership);
        organisationMembership.setOrganisation(this);
        return this;
    }

    public Organisation removeMemberships(OrganisationMembership organisationMembership) {
        this.memberships.remove(organisationMembership);
        organisationMembership.setOrganisation(null);
        return this;
    }

    public void setMemberships(Set<OrganisationMembership> organisationMemberships) {
        this.memberships = organisationMemberships;
    }

    public User getOwner() {
        return owner;
    }

    public Organisation owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public Set<Activity> getVisibleActivities() {
        return visibleActivities;
    }

    public Organisation visibleActivities(Set<Activity> activities) {
        this.visibleActivities = activities;
        return this;
    }

    public Organisation addVisibleActivities(Activity activity) {
        this.visibleActivities.add(activity);
        activity.getVisibleForOrganisations().add(this);
        return this;
    }

    public Organisation removeVisibleActivities(Activity activity) {
        this.visibleActivities.remove(activity);
        activity.getVisibleForOrganisations().remove(this);
        return this;
    }

    public void setVisibleActivities(Set<Activity> activities) {
        this.visibleActivities = activities;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organisation)) {
            return false;
        }
        return id != null && id.equals(((Organisation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organisation{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", created='" + getCreated() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
