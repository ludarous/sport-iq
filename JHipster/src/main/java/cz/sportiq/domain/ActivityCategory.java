package cz.sportiq.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ActivityCategory.
 */
@Entity
@Table(name = "activity_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "activitycategory")
public class ActivityCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "parentActivityCategory")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ActivityCategory> childActivityCategories = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("childActivityCategories")
    private ActivityCategory parentActivityCategory;

    @ManyToMany(mappedBy = "categories")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Activity> activities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ActivityCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ActivityCategory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ActivityCategory> getChildActivityCategories() {
        return childActivityCategories;
    }

    public ActivityCategory childActivityCategories(Set<ActivityCategory> activityCategories) {
        this.childActivityCategories = activityCategories;
        return this;
    }

    public ActivityCategory addChildActivityCategories(ActivityCategory activityCategory) {
        this.childActivityCategories.add(activityCategory);
        activityCategory.setParentActivityCategory(this);
        return this;
    }

    public ActivityCategory removeChildActivityCategories(ActivityCategory activityCategory) {
        this.childActivityCategories.remove(activityCategory);
        activityCategory.setParentActivityCategory(null);
        return this;
    }

    public void setChildActivityCategories(Set<ActivityCategory> activityCategories) {
        this.childActivityCategories = activityCategories;
    }

    public ActivityCategory getParentActivityCategory() {
        return parentActivityCategory;
    }

    public ActivityCategory parentActivityCategory(ActivityCategory activityCategory) {
        this.parentActivityCategory = activityCategory;
        return this;
    }

    public void setParentActivityCategory(ActivityCategory activityCategory) {
        this.parentActivityCategory = activityCategory;
    }

    public Set<Activity> getActivities() {
        return activities;
    }

    public ActivityCategory activities(Set<Activity> activities) {
        this.activities = activities;
        return this;
    }

    public ActivityCategory addActivities(Activity activity) {
        this.activities.add(activity);
        activity.getCategories().add(this);
        return this;
    }

    public ActivityCategory removeActivities(Activity activity) {
        this.activities.remove(activity);
        activity.getCategories().remove(this);
        return this;
    }

    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivityCategory)) {
            return false;
        }
        return id != null && id.equals(((ActivityCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ActivityCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
