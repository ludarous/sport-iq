package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.WorkoutCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity WorkoutCategory and its DTO WorkoutCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkoutCategoryMapper extends EntityMapper<WorkoutCategoryDTO, WorkoutCategory> {



    default WorkoutCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkoutCategory workoutCategory = new WorkoutCategory();
        workoutCategory.setId(id);
        return workoutCategory;
    }
}
