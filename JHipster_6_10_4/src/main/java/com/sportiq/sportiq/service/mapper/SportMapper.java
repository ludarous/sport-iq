package com.sportiq.sportiq.service.mapper;


import com.sportiq.sportiq.domain.*;
import com.sportiq.sportiq.service.dto.SportDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sport} and its DTO {@link SportDTO}.
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
