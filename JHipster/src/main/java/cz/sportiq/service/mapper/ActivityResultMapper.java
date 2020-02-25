package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.ActivityResultDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ActivityResult} and its DTO {@link ActivityResultDTO}.
 */
@Mapper(componentModel = "spring", uses = {ActivityMapper.class, UnitMapper.class, ActivityResultSplitMapper.class})
public interface ActivityResultMapper extends EntityMapper<ActivityResultDTO, ActivityResult> {

    @Mapping(source = "resultUnit", target = "resultUnit")
    @Mapping(source = "activity.id", target = "activityId")
    ActivityResultDTO toDto(ActivityResult activityResult);

    @Mapping(target = "resultSplits", ignore = true)
    @Mapping(target = "removeResultSplits", ignore = true)
    @Mapping(source = "activityId", target = "activity")
    ActivityResult toEntity(ActivityResultDTO activityResultDTO);

    default ActivityResult fromId(Long id) {
        if (id == null) {
            return null;
        }
        ActivityResult activityResult = new ActivityResult();
        activityResult.setId(id);
        return activityResult;
    }
}
