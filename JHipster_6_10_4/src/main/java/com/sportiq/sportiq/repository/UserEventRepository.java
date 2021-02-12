package com.sportiq.sportiq.repository;

import com.sportiq.sportiq.domain.UserEvent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the UserEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserEventRepository extends JpaRepository<UserEvent, Long> {

    @Query("select userEvent from UserEvent userEvent where userEvent.user.login = ?#{principal.preferredUsername}")
    List<UserEvent> findByUserIsCurrentUser();
}
