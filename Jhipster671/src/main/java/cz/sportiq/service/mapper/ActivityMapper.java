package cz.sportiq.service.mapper;


import cz.sportiq.domain.*;
import cz.sportiq.service.dto.ActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Activity} and its DTO {@link ActivityDTO}.
 */
@Mapper(componentModel = "spring", uses = {UnitMapper.class, ActivityCategoryMapper.class, WorkoutMapper.class})
public interface ActivityMapper extends EntityMapper<ActivityDTO, Activity> {

    @Mapping(source = "targetUnit.id", target = "targetUnitId")
    @Mapping(source = "targetUnit.name", target = "targetUnitName")
    ActivityDTO toDto(Activity activity);

    @Mapping(target = "activityResults", ignore = true)
    @Mapping(target = "removeActivityResults", ignore = true)
    @Mapping(source = "targetUnitId", target = "targetUnit")
    @Mapping(target = "removeCategories", ignore = true)
    @Mapping(target = "workouts", ignore = true)
    @Mapping(target = "removeWorkouts", ignore = true)
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