package com.sportiq.sportiq.repository;

import com.sportiq.sportiq.domain.UserProperties;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserProperties entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserPropertiesRepository extends JpaRepository<UserProperties, Long> {
}
