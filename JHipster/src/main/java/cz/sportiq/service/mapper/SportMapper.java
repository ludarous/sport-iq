package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.SportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sport} and its DTO {@link SportDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SportMapper extends EntityMapper<SportDTO, Sport> {


    @Mapping(target = "workouts", ignore = true)
    @Mapping(target = "removeWorkouts", ignore = true)
    Sport toEntity(SportDTO sportDTO);

    default Sport fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sport sport = new Sport();
        sport.setId(id);
        return sport;
    }
}
