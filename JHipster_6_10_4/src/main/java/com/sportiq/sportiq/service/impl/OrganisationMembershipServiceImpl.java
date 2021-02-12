package com.sportiq.sportiq.service.impl;

import com.sportiq.sportiq.service.OrganisationMembershipService;
import com.sportiq.sportiq.domain.OrganisationMembership;
import com.sportiq.sportiq.repository.OrganisationMembershipRepository;
import com.sportiq.sportiq.service.dto.OrganisationMembershipDTO;
import com.sportiq.sportiq.service.mapper.OrganisationMembershipMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OrganisationMembership}.
 */
@Service
@Transactional
public class OrganisationMembershipServiceImpl implements OrganisationMembershipService {

    private final Logger log = LoggerFactory.getLogger(OrganisationMembershipServiceImpl.class);

    private final OrganisationMembershipRepository organisationMembershipRepository;

    private final OrganisationMembershipMapper organisationMembershipMapper;

    public OrganisationMembershipServiceImpl(OrganisationMembershipRepository organisationMembershipRepository, OrganisationMembershipMapper organisationMembershipMapper) {
        this.organisationMembershipRepository = organisationMembershipRepository;
        this.organisationMembershipMapper = organisationMembershipMapper;
    }

    @Override
    public OrganisationMembershipDTO save(OrganisationMembershipDTO organisationMembershipDTO) {
        log.debug("Request to save OrganisationMembership : {}", organisationMembershipDTO);
        OrganisationMembership organisationMembership = organisationMembershipMapper.toEntity(organisationMembershipDTO);
        organisationMembership = organisationMembershipRepository.save(organisationMembership);
        return organisationMembershipMapper.toDto(organisationMembership);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganisationMembershipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrganisationMemberships");
        return organisationMembershipRepository.findAll(pageable)
            .map(organisationMembershipMapper::toDto);
    }


    public Page<OrganisationMembershipDTO> findAllWithEagerRelationships(Pageable pageable) {
        return organisationMembershipRepository.findAllWithEagerRelationships(pageable).map(organisationMembershipMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrganisationMembershipDTO> findOne(Long id) {
        log.debug("Request to get OrganisationMembership : {}", id);
        return organisationMembershipRepository.findOneWithEagerRelationships(id)
            .map(organisationMembershipMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrganisationMembership : {}", id);
        organisationMembershipRepository.deleteById(id);
    }
}
