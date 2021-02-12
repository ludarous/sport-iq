package com.sportiq.sportiq.service.mapper;


import com.sportiq.sportiq.domain.*;
import com.sportiq.sportiq.service.dto.GroupMembershipDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GroupMembership} and its DTO {@link GroupMembershipDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, MembershipRoleMapper.class, GroupMapper.class})
public interface GroupMembershipMapper extends EntityMapper<GroupMembershipDTO, GroupMembership> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "group.id", target = "groupId")
    GroupMembershipDTO toDto(GroupMembership groupMembership);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "removeRoles", ignore = true)
    @Mapping(source = "groupId", target = "group")
    GroupMembership toEntity(GroupMembershipDTO groupMembershipDTO);

    default GroupMembership fromId(Long id) {
        if (id == null) {
            return null;
        }
        GroupMembership groupMembership = new GroupMembership();
        groupMembership.setId(id);
        return groupMembership;
    }
}
