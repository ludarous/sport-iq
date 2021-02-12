package com.sportiq.sportiq.service.mapper;


import com.sportiq.sportiq.domain.*;
import com.sportiq.sportiq.service.dto.UserEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserEvent} and its DTO {@link UserEventDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, EventMapper.class})
public interface UserEventMapper extends EntityMapper<UserEventDTO, UserEvent> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "event.id", target = "eventId")
    UserEventDTO toDto(UserEvent userEvent);

    @Mapping(target = "athleteActivities", ignore = true)
    @Mapping(target = "removeAthleteActivities", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "eventId", target = "event")
    UserEvent toEntity(UserEventDTO userEventDTO);

    default UserEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserEvent userEvent = new UserEvent();
        userEvent.setId(id);
        return userEvent;
    }
}
