package com.sportiq.sportiq.service.impl;

import com.sportiq.sportiq.service.OrganisationService;
import com.sportiq.sportiq.domain.Organisation;
import com.sportiq.sportiq.repository.OrganisationRepository;
import com.sportiq.sportiq.service.dto.OrganisationDTO;
import com.sportiq.sportiq.service.mapper.OrganisationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Organisation}.
 */
@Service
@Transactional
public class OrganisationServiceImpl implements OrganisationService {

    private final Logger log = LoggerFactory.getLogger(OrganisationServiceImpl.class);

    private final OrganisationRepository organisationRepository;

    private final OrganisationMapper organisationMapper;

    public OrganisationServiceImpl(OrganisationRepository organisationRepository, OrganisationMapper organisationMapper) {
        this.organisationRepository = organisationRepository;
        this.organisationMapper = organisationMapper;
    }

    @Override
    public OrganisationDTO save(OrganisationDTO organisationDTO) {
        log.debug("Request to save Organisation : {}", organisationDTO);
        Organisation organisation = organisationMapper.toEntity(organisationDTO);
        organisation = organisationRepository.save(organisation);
        return organisationMapper.toDto(organisation);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrganisationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Organisations");
        return organisationRepository.findAll(pageable)
            .map(organisationMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OrganisationDTO> findOne(Long id) {
        log.debug("Request to get Organisation : {}", id);
        return organisationRepository.findById(id)
            .map(organisationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Organisation : {}", id);
        organisationRepository.deleteById(id);
    }
}
