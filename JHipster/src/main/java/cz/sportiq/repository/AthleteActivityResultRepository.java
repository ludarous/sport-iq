package cz.sportiq.repository;

import cz.sportiq.domain.AthleteActivityResult;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AthleteActivityResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AthleteActivityResultRepository extends JpaRepository<AthleteActivityResult, Long> {

}
