package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.EventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Event} and its DTO {@link EventDTO}.
 */
@Mapper(componentModel = "spring", uses = {AddressMapper.class, WorkoutMapper.class, AthleteMapper.class})
public interface EventMapper extends EntityMapper<EventDTO, Event> {

    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "address.street", target = "addressStreet")
    EventDTO toDto(Event event);

    @Mapping(target = "athleteEvents", ignore = true)
    @Mapping(target = "removeAthleteEvents", ignore = true)
    @Mapping(source = "addressId", target = "address")
    @Mapping(target = "removeTests", ignore = true)
    @Mapping(target = "removeAthletes", ignore = true)
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
