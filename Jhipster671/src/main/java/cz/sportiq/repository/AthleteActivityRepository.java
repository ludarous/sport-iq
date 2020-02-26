package cz.sportiq.repository;

import cz.sportiq.domain.AthleteActivity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AthleteActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AthleteActivityRepository extends JpaRepository<AthleteActivity, Long> {

}
