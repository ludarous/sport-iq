package com.sportiq.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A MembershipRole.
 */
@Entity
@Table(name = "membership_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MembershipRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<OrganisationMembership> organisationMemberships = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<GroupMembership> groupMemberships = new HashSet<>();

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

    public MembershipRole name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<OrganisationMembership> getOrganisationMemberships() {
        return organisationMemberships;
    }

    public MembershipRole organisationMemberships(Set<OrganisationMembership> organisationMemberships) {
        this.organisationMemberships = organisationMemberships;
        return this;
    }

    public MembershipRole addOrganisationMemberships(OrganisationMembership organisationMembership) {
        this.organisationMemberships.add(organisationMembership);
        organisationMembership.getRoles().add(this);
        return this;
    }

    public MembershipRole removeOrganisationMemberships(OrganisationMembership organisationMembership) {
        this.organisationMemberships.remove(organisationMembership);
        organisationMembership.getRoles().remove(this);
        return this;
    }

    public void setOrganisationMemberships(Set<OrganisationMembership> organisationMemberships) {
        this.organisationMemberships = organisationMemberships;
    }

    public Set<GroupMembership> getGroupMemberships() {
        return groupMemberships;
    }

    public MembershipRole groupMemberships(Set<GroupMembership> groupMemberships) {
        this.groupMemberships = groupMemberships;
        return this;
    }

    public MembershipRole addGroupMemberships(GroupMembership groupMembership) {
        this.groupMemberships.add(groupMembership);
        groupMembership.getRoles().add(this);
        return this;
    }

    public MembershipRole removeGroupMemberships(GroupMembership groupMembership) {
        this.groupMemberships.remove(groupMembership);
        groupMembership.getRoles().remove(this);
        return this;
    }

    public void setGroupMemberships(Set<GroupMembership> groupMemberships) {
        this.groupMemberships = groupMemberships;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MembershipRole)) {
            return false;
        }
        return id != null && id.equals(((MembershipRole) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MembershipRole{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
