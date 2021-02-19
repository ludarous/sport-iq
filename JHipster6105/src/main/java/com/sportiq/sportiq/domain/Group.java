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
 * A Group.
 */
@Entity
@Table(name = "jhi_group")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Group implements Serializable {

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

    @OneToMany(mappedBy = "group")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<GroupMembership> memberships = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "groups", allowSetters = true)
    private User owner;

    @ManyToMany(mappedBy = "visibleForGroups")
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

    public Group name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Group created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public Group description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<GroupMembership> getMemberships() {
        return memberships;
    }

    public Group memberships(Set<GroupMembership> groupMemberships) {
        this.memberships = groupMemberships;
        return this;
    }

    public Group addMemberships(GroupMembership groupMembership) {
        this.memberships.add(groupMembership);
        groupMembership.setGroup(this);
        return this;
    }

    public Group removeMemberships(GroupMembership groupMembership) {
        this.memberships.remove(groupMembership);
        groupMembership.setGroup(null);
        return this;
    }

    public void setMemberships(Set<GroupMembership> groupMemberships) {
        this.memberships = groupMemberships;
    }

    public User getOwner() {
        return owner;
    }

    public Group owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public Set<Activity> getVisibleActivities() {
        return visibleActivities;
    }

    public Group visibleActivities(Set<Activity> activities) {
        this.visibleActivities = activities;
        return this;
    }

    public Group addVisibleActivities(Activity activity) {
        this.visibleActivities.add(activity);
        activity.getVisibleForGroups().add(this);
        return this;
    }

    public Group removeVisibleActivities(Activity activity) {
        this.visibleActivities.remove(activity);
        activity.getVisibleForGroups().remove(this);
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
        if (!(o instanceof Group)) {
            return false;
        }
        return id != null && id.equals(((Group) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Group{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", created='" + getCreated() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
