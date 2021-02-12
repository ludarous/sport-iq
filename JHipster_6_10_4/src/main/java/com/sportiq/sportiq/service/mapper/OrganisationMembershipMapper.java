package com.sportiq.sportiq.service.mapper;


import com.sportiq.sportiq.domain.*;
import com.sportiq.sportiq.service.dto.OrganisationMembershipDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrganisationMembership} and its DTO {@link OrganisationMembershipDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, MembershipRoleMapper.class, OrganisationMapper.class})
public interface OrganisationMembershipMapper extends EntityMapper<OrganisationMembershipDTO, OrganisationMembership> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "organisation.id", target = "organisationId")
    OrganisationMembershipDTO toDto(OrganisationMembership organisationMembership);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "removeRoles", ignore = true)
    @Mapping(source = "organisationId", target = "organisation")
    OrganisationMembership toEntity(OrganisationMembershipDTO organisationMembershipDTO);

    default OrganisationMembership fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrganisationMembership organisationMembership = new OrganisationMembership();
        organisationMembership.setId(id);
        return organisationMembership;
    }
}
