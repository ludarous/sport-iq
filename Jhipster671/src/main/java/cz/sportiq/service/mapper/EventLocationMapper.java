package cz.sportiq.service.mapper;


import cz.sportiq.domain.*;
import cz.sportiq.service.dto.EventLocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EventLocation} and its DTO {@link EventLocationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EventLocationMapper extends EntityMapper<EventLocationDTO, EventLocation> {



    default EventLocation fromId(Long id) {
        if (id == null) {
            return null;
        }
        EventLocation eventLocation = new EventLocation();
        eventLocation.setId(id);
        return eventLocation;
    }
}
