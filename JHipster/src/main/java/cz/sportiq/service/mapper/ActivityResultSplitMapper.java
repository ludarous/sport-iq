package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.ActivityResultSplitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ActivityResultSplit} and its DTO {@link ActivityResultSplitDTO}.
 */
@Mapper(componentModel = "spring", uses = {UnitMapper.class, ActivityResultMapper.class})
public interface ActivityResultSplitMapper extends EntityMapper<ActivityResultSplitDTO, ActivityResultSplit> {

    @Mapping(source = "splitUnit.id", target = "splitUnitId")
    @Mapping(source = "splitUnit.name", target = "splitUnitName")
    @Mapping(source = "activityResult.id", target = "activityResultId")
    ActivityResultSplitDTO toDto(ActivityResultSplit activityResultSplit);

    @Mapping(source = "splitUnitId", target = "splitUnit")
    @Mapping(source = "activityResultId", target = "activityResult")
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
