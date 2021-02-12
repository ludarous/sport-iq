package com.sportiq.sportiq.service.mapper;


import com.sportiq.sportiq.domain.*;
import com.sportiq.sportiq.service.dto.EventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Event} and its DTO {@link EventDTO}.
 */
@Mapper(componentModel = "spring", uses = {EventLocationMapper.class, ActivityMapper.class, UserMapper.class})
public interface EventMapper extends EntityMapper<EventDTO, Event> {

    @Mapping(source = "eventLocation.id", target = "eventLocationId")
    @Mapping(source = "eventLocation.name", target = "eventLocationName")
    EventDTO toDto(Event event);

    @Mapping(source = "eventLocationId", target = "eventLocation")
    @Mapping(target = "removeActivities", ignore = true)
    @Mapping(target = "removeEntrants", ignore = true)
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
