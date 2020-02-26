package cz.sportiq.service.mapper;


import cz.sportiq.domain.*;
import cz.sportiq.service.dto.WorkoutDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Workout} and its DTO {@link WorkoutDTO}.
 */
@Mapper(componentModel = "spring", uses = {ActivityMapper.class, WorkoutCategoryMapper.class, SportMapper.class})
public interface WorkoutMapper extends EntityMapper<WorkoutDTO, Workout> {


    @Mapping(target = "removeActivities", ignore = true)
    @Mapping(target = "removeCategories", ignore = true)
    @Mapping(target = "removeSports", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "removeEvents", ignore = true)
    Workout toEntity(WorkoutDTO workoutDTO);

    default Workout fromId(Long id) {
        if (id == null) {
            return null;
        }
        Workout workout = new Workout();
        workout.setId(id);
        return workout;
    }
}
