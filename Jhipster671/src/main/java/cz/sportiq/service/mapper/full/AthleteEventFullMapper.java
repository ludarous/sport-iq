package cz.sportiq.service.mapper.full;

import cz.sportiq.domain.AthleteEvent;
import cz.sportiq.service.dto.AthleteEventDTO;
import cz.sportiq.service.mapper.AthleteMapper;
import cz.sportiq.service.mapper.EntityMapper;
import cz.sportiq.service.mapper.EventMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity AthleteEvent and its DTO AthleteEventDTO.
 */
@Mapper(componentModel = "spring", uses = {EventMapper.class, AthleteMapper.class, AthleteWorkoutFullMapper.class})
public interface AthleteEventFullMapper extends EntityMapper<AthleteEventDTO, AthleteEvent> {

    @Mapping(source = "event.id", target = "eventId")
    @Mapping(source = "athlete.id", target = "athleteId")
    AthleteEventDTO toDto(AthleteEvent athleteEvent);

    @Mapping(source = "eventId", target = "event")
    @Mapping(target = "athleteWorkouts", ignore = true)
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
