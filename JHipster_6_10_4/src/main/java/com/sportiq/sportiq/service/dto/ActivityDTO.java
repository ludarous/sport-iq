package com.sportiq.sportiq.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.sportiq.sportiq.domain.Activity} entity.
 */
public class ActivityDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private Boolean isPublic;

    private String description;

    private String help;


    private String createdById;

    private String createdByLogin;
    private Set<OrganisationDTO> visibleForOrganisations = new HashSet<>();
    private Set<GroupDTO> visibleForGroups = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String userId) {
        this.createdById = userId;
    }

    public String getCreatedByLogin() {
        return createdByLogin;
    }

    public void setCreatedByLogin(String userLogin) {
        this.createdByLogin = userLogin;
    }

    public Set<OrganisationDTO> getVisibleForOrganisations() {
        return visibleForOrganisations;
    }

    public void setVisibleForOrganisations(Set<OrganisationDTO> organisations) {
        this.visibleForOrganisations = organisations;
    }

    public Set<GroupDTO> getVisibleForGroups() {
        return visibleForGroups;
    }

    public void setVisibleForGroups(Set<GroupDTO> groups) {
        this.visibleForGroups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivityDTO)) {
            return false;
        }

        return id != null && id.equals(((ActivityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ActivityDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isPublic='" + isIsPublic() + "'" +
            ", description='" + getDescription() + "'" +
            ", help='" + getHelp() + "'" +
            ", createdById='" + getCreatedById() + "'" +
            ", createdByLogin='" + getCreatedByLogin() + "'" +
            ", visibleForOrganisations='" + getVisibleForOrganisations() + "'" +
            ", visibleForGroups='" + getVisibleForGroups() + "'" +
            "}";
    }
}
