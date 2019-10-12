package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.AthleteActivityResultDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AthleteActivityResult and its DTO AthleteActivityResultDTO.
 */
@Mapper(componentModel = "spring", uses = {AthleteActivityMapper.class, ActivityResultMapper.class})
public interface AthleteActivityResultMapper extends EntityMapper<AthleteActivityResultDTO, AthleteActivityResult> {

    @Mapping(source = "athleteActivity.id", target = "athleteActivityId")
    @Mapping(source = "activityResult.id", target = "activityResultId")
    @Mapping(source = "activityResult.name", target = "activityResultName")
    AthleteActivityResultDTO toDto(AthleteActivityResult athleteActivityResult);

    @Mapping(source = "athleteActivityId", target = "athleteActivity")
    @Mapping(target = "athleteActivityResultSplits", ignore = true)
    @Mapping(source = "activityResultId", target = "activityResult")
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
