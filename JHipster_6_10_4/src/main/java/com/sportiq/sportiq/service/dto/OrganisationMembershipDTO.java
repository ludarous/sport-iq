package com.sportiq.sportiq.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.sportiq.sportiq.domain.OrganisationMembership} entity.
 */
public class OrganisationMembershipDTO implements Serializable {
    
    private Long id;

    private ZonedDateTime created;


    private String userId;
    private Set<MembershipRoleDTO> roles = new HashSet<>();

    private Long organisationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<MembershipRoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<MembershipRoleDTO> membershipRoles) {
        this.roles = membershipRoles;
    }

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganisationMembershipDTO)) {
            return false;
        }

        return id != null && id.equals(((OrganisationMembershipDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganisationMembershipDTO{" +
            "id=" + getId() +
            ", created='" + getCreated() + "'" +
            ", userId='" + getUserId() + "'" +
            ", roles='" + getRoles() + "'" +
            ", organisationId=" + getOrganisationId() +
            "}";
    }
}
