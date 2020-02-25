package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.WorkoutCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkoutCategory} and its DTO {@link WorkoutCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkoutCategoryMapper extends EntityMapper<WorkoutCategoryDTO, WorkoutCategory> {


    @Mapping(target = "workouts", ignore = true)
    @Mapping(target = "removeWorkouts", ignore = true)
    WorkoutCategory toEntity(WorkoutCategoryDTO workoutCategoryDTO);

    default WorkoutCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkoutCategory workoutCategory = new WorkoutCategory();
        workoutCategory.setId(id);
        return workoutCategory;
    }
}
