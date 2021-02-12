package com.sportiq.sportiq.service.mapper;


import com.sportiq.sportiq.domain.*;
import com.sportiq.sportiq.service.dto.UserActivityResultDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserActivityResult} and its DTO {@link UserActivityResultDTO}.
 */
@Mapper(componentModel = "spring", uses = {ActivityResultMapper.class, UserActivityMapper.class})
public interface UserActivityResultMapper extends EntityMapper<UserActivityResultDTO, UserActivityResult> {

    @Mapping(source = "activityResult.id", target = "activityResultId")
    @Mapping(source = "activityResult.name", target = "activityResultName")
    @Mapping(source = "userActivity.id", target = "userActivityId")
    UserActivityResultDTO toDto(UserActivityResult userActivityResult);

    @Mapping(target = "athleteActivityResultSplits", ignore = true)
    @Mapping(target = "removeAthleteActivityResultSplits", ignore = true)
    @Mapping(source = "activityResultId", target = "activityResult")
    @Mapping(source = "userActivityId", target = "userActivity")
    UserActivityResult toEntity(UserActivityResultDTO userActivityResultDTO);

    default UserActivityResult fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserActivityResult userActivityResult = new UserActivityResult();
        userActivityResult.setId(id);
        return userActivityResult;
    }
}
