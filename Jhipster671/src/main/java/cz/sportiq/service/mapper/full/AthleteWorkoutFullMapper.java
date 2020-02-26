package cz.sportiq.service.mapper.full;

import cz.sportiq.domain.AthleteWorkout;
import cz.sportiq.service.dto.AthleteWorkoutDTO;
import cz.sportiq.service.mapper.AthleteActivityMapper;
import cz.sportiq.service.mapper.EntityMapper;
import cz.sportiq.service.mapper.WorkoutMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity AthleteWorkout and its DTO AthleteWorkoutDTO.
 */
@Mapper(componentModel = "spring", uses = {AthleteEventFullMapper.class, WorkoutMapper.class, AthleteActivityMapper.class})
public interface AthleteWorkoutFullMapper extends EntityMapper<AthleteWorkoutDTO, AthleteWorkout> {

    @Mapping(source = "athleteEvent.id", target = "athleteEventId")
    @Mapping(source = "workout.id", target = "workoutId")
    @Mapping(source = "workout.name", target = "workoutName")
    AthleteWorkoutDTO toDto(AthleteWorkout athleteWorkout);

    @Mapping(source = "athleteEventId", target = "athleteEvent")
    @Mapping(target = "athleteActivities", ignore = true)
    @Mapping(source = "workoutId", target = "workout")
    AthleteWorkout toEntity(AthleteWorkoutDTO athleteWorkoutDTO);

    default AthleteWorkout fromId(Long id) {
        if (id == null) {
            return null;
        }
        AthleteWorkout athleteWorkout = new AthleteWorkout();
        athleteWorkout.setId(id);
        return athleteWorkout;
    }
}
