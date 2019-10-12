package cz.sportiq.repository;

import cz.sportiq.domain.AthleteEvent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AthleteEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AthleteEventRepository extends JpaRepository<AthleteEvent, Long> {

}
