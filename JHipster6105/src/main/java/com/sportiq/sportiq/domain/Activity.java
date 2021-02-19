package com.sportiq.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Column(name = "description")
    private String description;

    @Column(name = "help")
    private String help;

    @OneToMany(mappedBy = "activity")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ActivityResult> activityResults = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "activities", allowSetters = true)
    private User createdBy;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "activity_visible_for_organisations",
               joinColumns = @JoinColumn(name = "activity_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "visible_for_organisations_id", referencedColumnName = "id"))
    private Set<Organisation> visibleForOrganisations = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "activity_visible_for_groups",
               joinColumns = @JoinColumn(name = "activity_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "visible_for_groups_id", referencedColumnName = "id"))
    private Set<Group> visibleForGroups = new HashSet<>();

    @ManyToMany(mappedBy = "activities")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Event> events = new HashSet<>();

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

    public Activity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsPublic() {
        return isPublic;
    }

    public Activity isPublic(Boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getDescription() {
        return description;
    }

    public Activity description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHelp() {
        return help;
    }

    public Activity help(String help) {
        this.help = help;
        return this;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public Set<ActivityResult> getActivityResults() {
        return activityResults;
    }

    public Activity activityResults(Set<ActivityResult> activityResults) {
        this.activityResults = activityResults;
        return this;
    }

    public Activity addActivityResults(ActivityResult activityResult) {
        this.activityResults.add(activityResult);
        activityResult.setActivity(this);
        return this;
    }

    public Activity removeActivityResults(ActivityResult activityResult) {
        this.activityResults.remove(activityResult);
        activityResult.setActivity(null);
        return this;
    }

    public void setActivityResults(Set<ActivityResult> activityResults) {
        this.activityResults = activityResults;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public Activity createdBy(User user) {
        this.createdBy = user;
        return this;
    }

    public void setCreatedBy(User user) {
        this.createdBy = user;
    }

    public Set<Organisation> getVisibleForOrganisations() {
        return visibleForOrganisations;
    }

    public Activity visibleForOrganisations(Set<Organisation> organisations) {
        this.visibleForOrganisations = organisations;
        return this;
    }

    public Activity addVisibleForOrganisations(Organisation organisation) {
        this.visibleForOrganisations.add(organisation);
        organisation.getVisibleActivities().add(this);
        return this;
    }

    public Activity removeVisibleForOrganisations(Organisation organisation) {
        this.visibleForOrganisations.remove(organisation);
        organisation.getVisibleActivities().remove(this);
        return this;
    }

    public void setVisibleForOrganisations(Set<Organisation> organisations) {
        this.visibleForOrganisations = organisations;
    }

    public Set<Group> getVisibleForGroups() {
        return visibleForGroups;
    }

    public Activity visibleForGroups(Set<Group> groups) {
        this.visibleForGroups = groups;
        return this;
    }

    public Activity addVisibleForGroups(Group group) {
        this.visibleForGroups.add(group);
        group.getVisibleActivities().add(this);
        return this;
    }

    public Activity removeVisibleForGroups(Group group) {
        this.visibleForGroups.remove(group);
        group.getVisibleActivities().remove(this);
        return this;
    }

    public void setVisibleForGroups(Set<Group> groups) {
        this.visibleForGroups = groups;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public Activity events(Set<Event> events) {
        this.events = events;
        return this;
    }

    public Activity addEvents(Event event) {
        this.events.add(event);
        event.getActivities().add(this);
        return this;
    }

    public Activity removeEvents(Event event) {
        this.events.remove(event);
        event.getActivities().remove(this);
        return this;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Activity)) {
            return false;
        }
        return id != null && id.equals(((Activity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isPublic='" + isIsPublic() + "'" +
            ", description='" + getDescription() + "'" +
            ", help='" + getHelp() + "'" +
            "}";
    }
}
