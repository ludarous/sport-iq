package com.sportiq.sportiq.repository;

import com.sportiq.sportiq.domain.GroupMembership;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the GroupMembership entity.
 */
@Repository
public interface GroupMembershipRepository extends JpaRepository<GroupMembership, Long> {

    @Query("select groupMembership from GroupMembership groupMembership where groupMembership.user.login = ?#{principal.username}")
    List<GroupMembership> findByUserIsCurrentUser();

    @Query(value = "select distinct groupMembership from GroupMembership groupMembership left join fetch groupMembership.roles",
        countQuery = "select count(distinct groupMembership) from GroupMembership groupMembership")
    Page<GroupMembership> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct groupMembership from GroupMembership groupMembership left join fetch groupMembership.roles")
    List<GroupMembership> findAllWithEagerRelationships();

    @Query("select groupMembership from GroupMembership groupMembership left join fetch groupMembership.roles where groupMembership.id =:id")
    Optional<GroupMembership> findOneWithEagerRelationships(@Param("id") Long id);
}
