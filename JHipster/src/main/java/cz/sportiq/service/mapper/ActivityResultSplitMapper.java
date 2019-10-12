package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.ActivityResultSplitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ActivityResultSplit and its DTO ActivityResultSplitDTO.
 */
@Mapper(componentModel = "spring", uses = {ActivityResultMapper.class, UnitMapper.class})
public interface ActivityResultSplitMapper extends EntityMapper<ActivityResultSplitDTO, ActivityResultSplit> {

    @Mapping(source = "activityResult.id", target = "activityResultId")
    @Mapping(source = "splitUnit.id", target = "splitUnitId")
    @Mapping(source = "splitUnit.name", target = "splitUnitName")
    ActivityResultSplitDTO toDto(ActivityResultSplit activityResultSplit);

    @Mapping(source = "activityResultId", target = "activityResult")
    @Mapping(source = "splitUnitId", target = "splitUnit")
    ActivityResultSplit toEntity(ActivityResultSplitDTO activityResultSplitDTO);

    default ActivityResultSplit fromId(Long id) {
        if (id == null) {
            return null;
        }
        ActivityResultSplit activityResultSplit = new ActivityResultSplit();
        activityResultSplit.setId(id);
        return activityResultSplit;
    }
}
