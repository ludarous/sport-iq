package com.sportiq.sportiq.repository;

import com.sportiq.sportiq.domain.LegalRepresentative;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LegalRepresentative entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LegalRepresentativeRepository extends JpaRepository<LegalRepresentative, Long> {
}
