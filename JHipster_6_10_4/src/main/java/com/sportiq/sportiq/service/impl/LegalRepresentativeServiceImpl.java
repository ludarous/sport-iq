package com.sportiq.sportiq.service.impl;

import com.sportiq.sportiq.service.LegalRepresentativeService;
import com.sportiq.sportiq.domain.LegalRepresentative;
import com.sportiq.sportiq.repository.LegalRepresentativeRepository;
import com.sportiq.sportiq.service.dto.LegalRepresentativeDTO;
import com.sportiq.sportiq.service.mapper.LegalRepresentativeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LegalRepresentative}.
 */
@Service
@Transactional
public class LegalRepresentativeServiceImpl implements LegalRepresentativeService {

    private final Logger log = LoggerFactory.getLogger(LegalRepresentativeServiceImpl.class);

    private final LegalRepresentativeRepository legalRepresentativeRepository;

    private final LegalRepresentativeMapper legalRepresentativeMapper;

    public LegalRepresentativeServiceImpl(LegalRepresentativeRepository legalRepresentativeRepository, LegalRepresentativeMapper legalRepresentativeMapper) {
        this.legalRepresentativeRepository = legalRepresentativeRepository;
        this.legalRepresentativeMapper = legalRepresentativeMapper;
    }

    @Override
    public LegalRepresentativeDTO save(LegalRepresentativeDTO legalRepresentativeDTO) {
        log.debug("Request to save LegalRepresentative : {}", legalRepresentativeDTO);
        LegalRepresentative legalRepresentative = legalRepresentativeMapper.toEntity(legalRepresentativeDTO);
        legalRepresentative = legalRepresentativeRepository.save(legalRepresentative);
        return legalRepresentativeMapper.toDto(legalRepresentative);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LegalRepresentativeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LegalRepresentatives");
        return legalRepresentativeRepository.findAll(pageable)
            .map(legalRepresentativeMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<LegalRepresentativeDTO> findOne(Long id) {
        log.debug("Request to get LegalRepresentative : {}", id);
        return legalRepresentativeRepository.findById(id)
            .map(legalRepresentativeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LegalRepresentative : {}", id);
        legalRepresentativeRepository.deleteById(id);
    }
}
