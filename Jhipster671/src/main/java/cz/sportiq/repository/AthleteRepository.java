package cz.sportiq.repository;

import cz.sportiq.domain.Athlete;

import cz.sportiq.domain.AthleteActivityResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Athlete entity.
 */
@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {
    Optional<Athlete> findOneByEmail(String email);

    @Query(value = "select athlete from Athlete athlete " +
        "join athlete.user user " +
        "where user.id = :userId")
    Optional<Athlete> findOneByUserId(@Param("userId") String userId);

    @Query(value = "select distinct athlete from Athlete athlete left join fetch athlete.sports",
        countQuery = "select count(distinct athlete) from Athlete athlete")
    Page<Athlete> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct athlete from Athlete athlete left join fetch athlete.sports")
    List<Athlete> findAllWithEagerRelationships();

    @Query("select athlete from Athlete athlete left join fetch athlete.sports where athlete.id =:id")
    Optional<Athlete> findOneWithEagerRelationships(@Param("id") Long id);

}
