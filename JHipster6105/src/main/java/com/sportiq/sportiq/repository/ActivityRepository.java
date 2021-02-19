package com.sportiq.sportiq.repository;

import com.sportiq.sportiq.domain.Activity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Activity entity.
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query("select activity from Activity activity where activity.createdBy.login = ?#{principal.username}")
    List<Activity> findByCreatedByIsCurrentUser();

    @Query(value = "select distinct activity from Activity activity left join fetch activity.visibleForOrganisations left join fetch activity.visibleForGroups",
        countQuery = "select count(distinct activity) from Activity activity")
    Page<Activity> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct activity from Activity activity left join fetch activity.visibleForOrganisations left join fetch activity.visibleForGroups")
    List<Activity> findAllWithEagerRelationships();

    @Query("select activity from Activity activity left join fetch activity.visibleForOrganisations left join fetch activity.visibleForGroups where activity.id =:id")
    Optional<Activity> findOneWithEagerRelationships(@Param("id") Long id);
}
