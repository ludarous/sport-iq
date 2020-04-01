package cz.sportiq.service.mapper;


import cz.sportiq.domain.*;
import cz.sportiq.service.dto.AthleteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Athlete} and its DTO {@link AthleteDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, SportMapper.class})
public interface AthleteMapper extends EntityMapper<AthleteDTO, Athlete> {

    @Mapping(source = "user.id", target = "userId")
    AthleteDTO toDto(Athlete athlete);

    @Mapping(source = "userId", target = "user")
    @Mapping(target = "removeSports", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "removeEvents", ignore = true)
    Athlete toEntity(AthleteDTO athleteDTO);

    default Athlete fromId(Long id) {
        if (id == null) {
            return null;
        }
        Athlete athlete = new Athlete();
        athlete.setId(id);
        return athlete;
    }
}
