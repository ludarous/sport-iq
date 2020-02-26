package cz.sportiq.service.mapper;


import cz.sportiq.domain.*;
import cz.sportiq.service.dto.EventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Event} and its DTO {@link EventDTO}.
 */
@Mapper(componentModel = "spring", uses = {EventLocationMapper.class, WorkoutMapper.class, AthleteMapper.class})
public interface EventMapper extends EntityMapper<EventDTO, Event> {

    @Mapping(source = "eventLocation", target = "eventLocation")
    EventDTO toDto(Event event);

    @Mapping(target = "athleteEvents", ignore = true)
    @Mapping(target = "removeAthleteEvents", ignore = true)
    @Mapping(source = "eventLocation", target = "eventLocation")
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
