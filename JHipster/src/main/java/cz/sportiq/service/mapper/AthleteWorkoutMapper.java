package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.AthleteWorkoutDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AthleteWorkout} and its DTO {@link AthleteWorkoutDTO}.
 */
@Mapper(componentModel = "spring", uses = {AthleteEventMapper.class, WorkoutMapper.class})
public interface AthleteWorkoutMapper extends EntityMapper<AthleteWorkoutDTO, AthleteWorkout> {

    @Mapping(source = "athleteEvent.id", target = "athleteEventId")
    @Mapping(source = "workout.id", target = "workoutId")
    @Mapping(source = "workout.name", target = "workoutName")
    AthleteWorkoutDTO toDto(AthleteWorkout athleteWorkout);

    @Mapping(source = "athleteEventId", target = "athleteEvent")
    @Mapping(target = "athleteActivities", ignore = true)
    @Mapping(target = "removeAthleteActivities", ignore = true)
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
