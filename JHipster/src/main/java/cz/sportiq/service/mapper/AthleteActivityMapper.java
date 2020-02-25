package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.AthleteActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AthleteActivity} and its DTO {@link AthleteActivityDTO}.
 */
@Mapper(componentModel = "spring", uses = {AthleteWorkoutMapper.class, ActivityMapper.class, AthleteActivityResultMapper.class})
public interface AthleteActivityMapper extends EntityMapper<AthleteActivityDTO, AthleteActivity> {

    @Mapping(source = "activity.id", target = "activityId")
    @Mapping(source = "activity.name", target = "activityName")
    @Mapping(source = "athleteWorkout.id", target = "athleteWorkoutId")
    AthleteActivityDTO toDto(AthleteActivity athleteActivity);

    @Mapping(target = "removeAthleteActivityResults", ignore = true)
    @Mapping(source = "activityId", target = "activity")
    @Mapping(source = "athleteWorkoutId", target = "athleteWorkout")
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
