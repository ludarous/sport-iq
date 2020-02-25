package cz.sportiq.repository;
import cz.sportiq.domain.Athlete;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Athlete entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {
    Optional<Athlete> finOneByEmail(String email);

    Optional<Athlete> finOneByUserId(String email);
}
