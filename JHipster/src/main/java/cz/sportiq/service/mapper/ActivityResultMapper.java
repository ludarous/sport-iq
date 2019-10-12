package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.ActivityResultDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ActivityResult and its DTO ActivityResultDTO.
 */
@Mapper(componentModel = "spring", uses = {ActivityMapper.class, UnitMapper.class})
public interface ActivityResultMapper extends EntityMapper<ActivityResultDTO, ActivityResult> {

    @Mapping(source = "activity.id", target = "activityId")
    @Mapping(source = "resultUnit.id", target = "resultUnitId")
    @Mapping(source = "resultUnit.name", target = "resultUnitName")
    ActivityResultDTO toDto(ActivityResult activityResult);

    @Mapping(source = "activityId", target = "activity")
    @Mapping(target = "resultSplits", ignore = true)
    @Mapping(source = "resultUnitId", target = "resultUnit")
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
