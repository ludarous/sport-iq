package com.sportiq.sportiq.service.mapper;


import com.sportiq.sportiq.domain.*;
import com.sportiq.sportiq.service.dto.MembershipRoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MembershipRole} and its DTO {@link MembershipRoleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MembershipRoleMapper extends EntityMapper<MembershipRoleDTO, MembershipRole> {


    @Mapping(target = "organisationMemberships", ignore = true)
    @Mapping(target = "removeOrganisationMemberships", ignore = true)
    @Mapping(target = "groupMemberships", ignore = true)
    @Mapping(target = "removeGroupMemberships", ignore = true)
    MembershipRole toEntity(MembershipRoleDTO membershipRoleDTO);

    default MembershipRole fromId(Long id) {
        if (id == null) {
            return null;
        }
        MembershipRole membershipRole = new MembershipRole();
        membershipRole.setId(id);
        return membershipRole;
    }
}
