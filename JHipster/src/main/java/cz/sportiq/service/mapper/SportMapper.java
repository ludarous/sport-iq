package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.SportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sport and its DTO SportDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SportMapper extends EntityMapper<SportDTO, Sport> {



    default Sport fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sport sport = new Sport();
        sport.setId(id);
        return sport;
    }
}
