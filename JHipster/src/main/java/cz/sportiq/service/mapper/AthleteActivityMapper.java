package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.AthleteActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AthleteActivity and its DTO AthleteActivityDTO.
 */
@Mapper(componentModel = "spring", uses = {AthleteWorkoutMapper.class, ActivityMapper.class, AthleteActivityResultMapper.class})
public interface AthleteActivityMapper extends EntityMapper<AthleteActivityDTO, AthleteActivity> {

    @Mapping(source = "athleteWorkout.id", target = "athleteWorkoutId")
    @Mapping(source = "activity.id", target = "activityId")
    @Mapping(source = "activity.name", target = "activityName")
    AthleteActivityDTO toDto(AthleteActivity athleteActivity);

    @Mapping(source = "athleteWorkoutId", target = "athleteWorkout")
    @Mapping(source = "activityId", target = "activity")
    AthleteActivity toEntity(AthleteActivityDTO athleteActivityDTO);

    default AthleteActivity fromId(Long id) {
        if (id == null) {
            return null;
        }
        AthleteActivity athleteActivity = new AthleteActivity();
        athleteActivity.setId(id);
        return athleteActivity;
    }
}
