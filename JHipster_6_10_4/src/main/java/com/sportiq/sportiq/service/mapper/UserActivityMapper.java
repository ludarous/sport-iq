package com.sportiq.sportiq.service.mapper;


import com.sportiq.sportiq.domain.*;
import com.sportiq.sportiq.service.dto.UserActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserActivity} and its DTO {@link UserActivityDTO}.
 */
@Mapper(componentModel = "spring", uses = {ActivityMapper.class, UserEventMapper.class})
public interface UserActivityMapper extends EntityMapper<UserActivityDTO, UserActivity> {

    @Mapping(source = "activity.id", target = "activityId")
    @Mapping(source = "activity.name", target = "activityName")
    @Mapping(source = "userEvent.id", target = "userEventId")
    UserActivityDTO toDto(UserActivity userActivity);

    @Mapping(target = "athleteActivityResults", ignore = true)
    @Mapping(target = "removeAthleteActivityResults", ignore = true)
    @Mapping(source = "activityId", target = "activity")
    @Mapping(source = "userEventId", target = "userEvent")
    UserActivity toEntity(UserActivityDTO userActivityDTO);

    default UserActivity fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserActivity userActivity = new UserActivity();
        userActivity.setId(id);
        return userActivity;
    }
}
