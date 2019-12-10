package cz.sportiq.repository;

import cz.sportiq.domain.AthleteEvent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;


/**
 * Spring Data  repository for the AthleteEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AthleteEventRepository extends JpaRepository<AthleteEvent, Long> {

    Optional<AthleteEvent> findByEventIdAndAthleteId(Long eventId, Long athleteId);

    Set<AthleteEvent> findByAthleteId(Long athleteId);
}
