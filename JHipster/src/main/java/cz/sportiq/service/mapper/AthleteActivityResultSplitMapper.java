package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.AthleteActivityResultSplitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AthleteActivityResultSplit and its DTO AthleteActivityResultSplitDTO.
 */
@Mapper(componentModel = "spring", uses = {AthleteActivityResultMapper.class, ActivityResultSplitMapper.class})
public interface AthleteActivityResultSplitMapper extends EntityMapper<AthleteActivityResultSplitDTO, AthleteActivityResultSplit> {

    @Mapping(source = "athleteActivityResult.id", target = "athleteActivityResultId")
    @Mapping(source = "activityResultSplit.id", target = "activityResultSplitId")
    AthleteActivityResultSplitDTO toDto(AthleteActivityResultSplit athleteActivityResultSplit);

    @Mapping(source = "athleteActivityResultId", target = "athleteActivityResult")
    @Mapping(source = "activityResultSplitId", target = "activityResultSplit")
    AthleteActivityResultSplit toEntity(AthleteActivityResultSplitDTO athleteActivityResultSplitDTO);

    default AthleteActivityResultSplit fromId(Long id) {
        if (id == null) {
            return null;
        }
        AthleteActivityResultSplit athleteActivityResultSplit = new AthleteActivityResultSplit();
        athleteActivityResultSplit.setId(id);
        return athleteActivityResultSplit;
    }
}
