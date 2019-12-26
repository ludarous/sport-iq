package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.AthleteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Athlete} and its DTO {@link AthleteDTO}.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface AthleteMapper extends EntityMapper<AthleteDTO, Athlete> {

    @Mapping(source = "address.id", target = "addressId")
    AthleteDTO toDto(Athlete athlete);

    @Mapping(source = "addressId", target = "address")
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
