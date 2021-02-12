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
 * A GroupMembership.
 */
@Entity
@Table(name = "group_membership")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupMembership implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "created")
    private ZonedDateTime created;

    @ManyToOne
    @JsonIgnoreProperties(value = "groupMemberships", allowSetters = true)
    private User user;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "group_membership_roles",
               joinColumns = @JoinColumn(name = "group_membership_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Set<MembershipRole> roles = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "memberships", allowSetters = true)
    private Group group;

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

    public GroupMembership created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public GroupMembership user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<MembershipRole> getRoles() {
        return roles;
    }

    public GroupMembership roles(Set<MembershipRole> membershipRoles) {
        this.roles = membershipRoles;
        return this;
    }

    public GroupMembership addRoles(MembershipRole membershipRole) {
        this.roles.add(membershipRole);
        membershipRole.getGroupMemberships().add(this);
        return this;
    }

    public GroupMembership removeRoles(MembershipRole membershipRole) {
        this.roles.remove(membershipRole);
        membershipRole.getGroupMemberships().remove(this);
        return this;
    }

    public void setRoles(Set<MembershipRole> membershipRoles) {
        this.roles = membershipRoles;
    }

    public Group getGroup() {
        return group;
    }

    public GroupMembership group(Group group) {
        this.group = group;
        return this;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupMembership)) {
            return false;
        }
        return id != null && id.equals(((GroupMembership) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupMembership{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
