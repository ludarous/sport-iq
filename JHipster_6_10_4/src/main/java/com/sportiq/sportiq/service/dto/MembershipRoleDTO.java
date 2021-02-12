package com.sportiq.sportiq.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.sportiq.sportiq.domain.MembershipRole} entity.
 */
public class MembershipRoleDTO implements Serializable {
    
    private Long id;

    private String name;

    
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MembershipRoleDTO)) {
            return false;
        }

        return id != null && id.equals(((MembershipRoleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MembershipRoleDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
