package cz.sportiq.service.mapper;


import cz.sportiq.domain.*;
import cz.sportiq.service.dto.AthleteActivityResultSplitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AthleteActivityResultSplit} and its DTO {@link AthleteActivityResultSplitDTO}.
 */
@Mapper(componentModel = "spring", uses = {ActivityResultSplitMapper.class, AthleteActivityResultMapper.class})
public interface AthleteActivityResultSplitMapper extends EntityMapper<AthleteActivityResultSplitDTO, AthleteActivityResultSplit> {

    @Mapping(source = "activityResultSplit.id", target = "activityResultSplitId")
    @Mapping(source = "athleteActivityResult.id", target = "athleteActivityResultId")
    AthleteActivityResultSplitDTO toDto(AthleteActivityResultSplit athleteActivityResultSplit);

    @Mapping(source = "activityResultSplitId", target = "activityResultSplit")
    @Mapping(source = "athleteActivityResultId", target = "athleteActivityResult")
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
