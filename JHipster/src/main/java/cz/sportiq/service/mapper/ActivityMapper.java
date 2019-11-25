package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.ActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Activity and its DTO ActivityDTO.
 */
@Mapper(componentModel = "spring", uses = {UnitMapper.class, ActivityCategoryMapper.class, ActivityResultMapper.class})
public interface ActivityMapper extends EntityMapper<ActivityDTO, Activity> {

    @Mapping(source = "targetUnit.id", target = "targetUnitId")
    @Mapping(source = "targetUnit.name", target = "targetUnitName")
    ActivityDTO toDto(Activity activity);

    @Mapping(target = "activityResults", ignore = true)
    @Mapping(source = "targetUnitId", target = "targetUnit")
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
