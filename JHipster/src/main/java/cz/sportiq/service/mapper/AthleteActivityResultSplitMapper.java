package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.AthleteActivityResultSplitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AthleteActivityResultSplit and its DTO AthleteActivityResultSplitDTO.
 */
@Mapper(componentModel = "spring", uses = {AthleteActivityResultMapper.class})
public interface AthleteActivityResultSplitMapper extends EntityMapper<AthleteActivityResultSplitDTO, AthleteActivityResultSplit> {

    @Mapping(source = "athleteActivityResult.id", target = "athleteActivityResultId")
    AthleteActivityResultSplitDTO toDto(AthleteActivityResultSplit athleteActivityResultSplit);

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
