package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.AthleteActivityResultDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AthleteActivityResult} and its DTO {@link AthleteActivityResultDTO}.
 */
@Mapper(componentModel = "spring", uses = {AthleteActivityMapper.class, ActivityResultMapper.class, AthleteActivityResultSplitMapper.class})
public interface AthleteActivityResultMapper extends EntityMapper<AthleteActivityResultDTO, AthleteActivityResult> {

    @Mapping(source = "activityResult.id", target = "activityResultId")
    @Mapping(source = "activityResult.name", target = "activityResultName")
    @Mapping(source = "athleteActivity.id", target = "athleteActivityId")
    AthleteActivityResultDTO toDto(AthleteActivityResult athleteActivityResult);

    @Mapping(target = "removeAthleteActivityResultSplits", ignore = true)
    @Mapping(source = "activityResultId", target = "activityResult")
    @Mapping(source = "athleteActivityId", target = "athleteActivity")
    AthleteActivityResult toEntity(AthleteActivityResultDTO athleteActivityResultDTO);

    default AthleteActivityResult fromId(Long id) {
        if (id == null) {
            return null;
        }
        AthleteActivityResult athleteActivityResult = new AthleteActivityResult();
        athleteActivityResult.setId(id);
        return athleteActivityResult;
    }
}
