package cz.sportiq.repository;

import cz.sportiq.domain.EventLocation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EventLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventLocationRepository extends JpaRepository<EventLocation, Long> {

}
