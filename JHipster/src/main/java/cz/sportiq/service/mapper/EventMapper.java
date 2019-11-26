package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.EventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Event and its DTO EventDTO.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class, WorkoutMapper.class, AthleteMapper.class})
public interface EventMapper extends EntityMapper<EventDTO, Event> {

    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "address.street", target = "addressStreet")
    EventDTO toDto(Event event);

    @Mapping(target = "athleteEvents", ignore = true)
    @Mapping(source = "addressId", target = "address")
    Event toEntity(EventDTO eventDTO);

    default Event fromId(Long id) {
        if (id == null) {
            return null;
        }
        Event event = new Event();
        event.setId(id);
        return event;
    }
}
