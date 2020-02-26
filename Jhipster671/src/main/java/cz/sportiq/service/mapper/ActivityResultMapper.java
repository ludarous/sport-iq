package cz.sportiq.service.mapper;


import cz.sportiq.domain.*;
import cz.sportiq.service.dto.ActivityResultDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ActivityResult} and its DTO {@link ActivityResultDTO}.
 */
@Mapper(componentModel = "spring", uses = {UnitMapper.class, ActivityMapper.class})
public interface ActivityResultMapper extends EntityMapper<ActivityResultDTO, ActivityResult> {

    @Mapping(source = "resultUnit.id", target = "resultUnitId")
    @Mapping(source = "resultUnit.name", target = "resultUnitName")
    @Mapping(source = "activity.id", target = "activityId")
    ActivityResultDTO toDto(ActivityResult activityResult);

    @Mapping(target = "resultSplits", ignore = true)
    @Mapping(target = "removeResultSplits", ignore = true)
    @Mapping(source = "resultUnitId", target = "resultUnit")
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
