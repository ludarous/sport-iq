package cz.sportiq.repository;

import cz.sportiq.domain.Athlete;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Athlete entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {

}
