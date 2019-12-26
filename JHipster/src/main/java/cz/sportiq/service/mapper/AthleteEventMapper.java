package cz.sportiq.service.mapper;

import cz.sportiq.domain.*;
import cz.sportiq.service.dto.AthleteEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AthleteEvent} and its DTO {@link AthleteEventDTO}.
 */
@Mapper(componentModel = "spring", uses = {EventMapper.class, AthleteMapper.class})
public interface AthleteEventMapper extends EntityMapper<AthleteEventDTO, AthleteEvent> {

    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "event.name", target = "eventName")
    @Mapping(source = "athlete.id", target = "athleteId")
    AthleteEventDTO toDto(AthleteEvent athleteEvent);

    @Mapping(source = "eventId", target = "event")
    @Mapping(target = "athleteWorkouts", ignore = true)
    @Mapping(target = "removeAthleteWorkouts", ignore = true)
    @Mapping(source = "athleteId", target = "athlete")
    AthleteEvent toEntity(AthleteEventDTO athleteEventDTO);

    default AthleteEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        AthleteEvent athleteEvent = new AthleteEvent();
        athleteEvent.setId(id);
        return athleteEvent;
    }
}
