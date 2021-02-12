package com.sportiq.sportiq.service.mapper;


import com.sportiq.sportiq.domain.*;
import com.sportiq.sportiq.service.dto.UserActivityResultSplitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserActivityResultSplit} and its DTO {@link UserActivityResultSplitDTO}.
 */
@Mapper(componentModel = "spring", uses = {ActivityResultSplitMapper.class, UserActivityResultMapper.class})
public interface UserActivityResultSplitMapper extends EntityMapper<UserActivityResultSplitDTO, UserActivityResultSplit> {

    @Mapping(source = "activityResultSplit.id", target = "activityResultSplitId")
    @Mapping(source = "userActivityResult.id", target = "userActivityResultId")
    UserActivityResultSplitDTO toDto(UserActivityResultSplit userActivityResultSplit);

    @Mapping(source = "activityResultSplitId", target = "activityResultSplit")
    @Mapping(source = "userActivityResultId", target = "userActivityResult")
    UserActivityResultSplit toEntity(UserActivityResultSplitDTO userActivityResultSplitDTO);

    default UserActivityResultSplit fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserActivityResultSplit userActivityResultSplit = new UserActivityResultSplit();
        userActivityResultSplit.setId(id);
        return userActivityResultSplit;
    }
}
