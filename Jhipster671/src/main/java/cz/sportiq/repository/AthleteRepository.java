package cz.sportiq.repository;

import cz.sportiq.domain.Athlete;

import cz.sportiq.domain.AthleteActivityResult;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Athlete entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {
    Optional<Athlete> findOneByEmail(String email);

    @Query("select athlete from Athlete athlete " +
        "join athlete.user user " +
        "where user.id = :userId")
    Optional<Athlete> findOneByUserId(@Param("userId") String userId);
}
