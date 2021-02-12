package com.sportiq.sportiq.service.mapper;


import com.sportiq.sportiq.domain.*;
import com.sportiq.sportiq.service.dto.GroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Group} and its DTO {@link GroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface GroupMapper extends EntityMapper<GroupDTO, Group> {

    @Mapping(source = "owner.id", target = "ownerId")
    GroupDTO toDto(Group group);

    @Mapping(target = "memberships", ignore = true)
    @Mapping(target = "removeMemberships", ignore = true)
    @Mapping(source = "ownerId", target = "owner")
    @Mapping(target = "visibleActivities", ignore = true)
    @Mapping(target = "removeVisibleActivities", ignore = true)
    Group toEntity(GroupDTO groupDTO);

    default Group fromId(Long id) {
        if (id == null) {
            return null;
        }
        Group group = new Group();
        group.setId(id);
        return group;
    }
}
