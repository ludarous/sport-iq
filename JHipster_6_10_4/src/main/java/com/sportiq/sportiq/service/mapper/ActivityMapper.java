package com.sportiq.sportiq.service.mapper;


import com.sportiq.sportiq.domain.*;
import com.sportiq.sportiq.service.dto.ActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Activity} and its DTO {@link ActivityDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, OrganisationMapper.class, GroupMapper.class})
public interface ActivityMapper extends EntityMapper<ActivityDTO, Activity> {

    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "createdBy.login", target = "createdByLogin")
    ActivityDTO toDto(Activity activity);

    @Mapping(target = "activityResults", ignore = true)
    @Mapping(target = "removeActivityResults", ignore = true)
    @Mapping(source = "createdById", target = "createdBy")
    @Mapping(target = "removeVisibleForOrganisations", ignore = true)
    @Mapping(target = "removeVisibleForGroups", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "removeEvents", ignore = true)
    Activity toEntity(ActivityDTO activityDTO);

    default Activity fromId(Long id) {
        if (id == null) {
            return null;
        }
        Activity activity = new Activity();
        activity.setId(id);
        return activity;
    }
}
