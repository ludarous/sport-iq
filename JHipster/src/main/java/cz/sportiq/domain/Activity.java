package cz.sportiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Activity.
 */
@Entity
@Table(name = "activity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "help")
    private String help;

    @Column(name = "min_age")
    private Integer minAge;

    @Column(name = "max_age")
    private Integer maxAge;

    @Column(name = "target_value")
    private Float targetValue;

    @OneToMany(mappedBy = "activity")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ActivityResult> activityResults = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("")
    private Unit targetUnit;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "activity_categories",
               joinColumns = @JoinColumn(name = "activities_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "id"))
    private Set<ActivityCategory> categories = new HashSet<>();

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

    public Activity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getMinAge() {
        return minAge;
    }

    public Activity minAge(Integer minAge) {
        this.minAge = minAge;
        return this;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public Activity maxAge(Integer maxAge) {
        this.maxAge = maxAge;
        return this;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Float getTargetValue() {
        return targetValue;
    }

    public Activity targetValue(Float targetValue) {
        this.targetValue = targetValue;
        return this;
    }

    public void setTargetValue(Float targetValue) {
        this.targetValue = targetValue;
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

    public Unit getTargetUnit() {
        return targetUnit;
    }

    public Activity targetUnit(Unit unit) {
        this.targetUnit = unit;
        return this;
    }

    public void setTargetUnit(Unit unit) {
        this.targetUnit = unit;
    }

    public Set<ActivityCategory> getCategories() {
        return categories;
    }

    public Activity categories(Set<ActivityCategory> activityCategories) {
        this.categories = activityCategories;
        return this;
    }

    public Activity addCategories(ActivityCategory activityCategory) {
        this.categories.add(activityCategory);
        return this;
    }

    public Activity removeCategories(ActivityCategory activityCategory) {
        this.categories.remove(activityCategory);
        return this;
    }

    public void setCategories(Set<ActivityCategory> activityCategories) {
        this.categories = activityCategories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activity activity = (Activity) o;
        if (activity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Activity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", help='" + getHelp() + "'" +
            ", minAge=" + getMinAge() +
            ", maxAge=" + getMaxAge() +
            ", targetValue=" + getTargetValue() +
            "}";
    }
}