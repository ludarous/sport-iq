package com.sportiq.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A OrganisationMembership.
 */
@Entity
@Table(name = "organisation_membership")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrganisationMembership implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "created")
    private ZonedDateTime created;

    @ManyToOne
    @JsonIgnoreProperties(value = "organisationMemberships", allowSetters = true)
    private User user;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "organisation_membership_roles",
               joinColumns = @JoinColumn(name = "organisation_membership_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Set<MembershipRole> roles = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "memberships", allowSetters = true)
    private Organisation organisation;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public OrganisationMembership created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public OrganisationMembership user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<MembershipRole> getRoles() {
        return roles;
    }

    public OrganisationMembership roles(Set<MembershipRole> membershipRoles) {
        this.roles = membershipRoles;
        return this;
    }

    public OrganisationMembership addRoles(MembershipRole membershipRole) {
        this.roles.add(membershipRole);
        membershipRole.getOrganisationMemberships().add(this);
        return this;
    }

    public OrganisationMembership removeRoles(MembershipRole membershipRole) {
        this.roles.remove(membershipRole);
        membershipRole.getOrganisationMemberships().remove(this);
        return this;
    }

    public void setRoles(Set<MembershipRole> membershipRoles) {
        this.roles = membershipRoles;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public OrganisationMembership organisation(Organisation organisation) {
        this.organisation = organisation;
        return this;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganisationMembership)) {
            return false;
        }
        return id != null && id.equals(((OrganisationMembership) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganisationMembership{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
