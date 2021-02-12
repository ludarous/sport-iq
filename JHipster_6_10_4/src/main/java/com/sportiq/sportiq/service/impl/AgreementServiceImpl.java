package com.sportiq.sportiq.service.impl;

import com.sportiq.sportiq.service.AgreementService;
import com.sportiq.sportiq.domain.Agreement;
import com.sportiq.sportiq.repository.AgreementRepository;
import com.sportiq.sportiq.service.dto.AgreementDTO;
import com.sportiq.sportiq.service.mapper.AgreementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Agreement}.
 */
@Service
@Transactional
public class AgreementServiceImpl implements AgreementService {

    private final Logger log = LoggerFactory.getLogger(AgreementServiceImpl.class);

    private final AgreementRepository agreementRepository;

    private final AgreementMapper agreementMapper;

    public AgreementServiceImpl(AgreementRepository agreementRepository, AgreementMapper agreementMapper) {
        this.agreementRepository = agreementRepository;
        this.agreementMapper = agreementMapper;
    }

    @Override
    public AgreementDTO save(AgreementDTO agreementDTO) {
        log.debug("Request to save Agreement : {}", agreementDTO);
        Agreement agreement = agreementMapper.toEntity(agreementDTO);
        agreement = agreementRepository.save(agreement);
        return agreementMapper.toDto(agreement);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgreementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Agreements");
        return agreementRepository.findAll(pageable)
            .map(agreementMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AgreementDTO> findOne(Long id) {
        log.debug("Request to get Agreement : {}", id);
        return agreementRepository.findById(id)
            .map(agreementMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Agreement : {}", id);
        agreementRepository.deleteById(id);
    }
}
