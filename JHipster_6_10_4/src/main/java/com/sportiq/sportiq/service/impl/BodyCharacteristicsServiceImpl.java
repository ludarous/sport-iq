package com.sportiq.sportiq.service.impl;

import com.sportiq.sportiq.service.BodyCharacteristicsService;
import com.sportiq.sportiq.domain.BodyCharacteristics;
import com.sportiq.sportiq.repository.BodyCharacteristicsRepository;
import com.sportiq.sportiq.service.dto.BodyCharacteristicsDTO;
import com.sportiq.sportiq.service.mapper.BodyCharacteristicsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BodyCharacteristics}.
 */
@Service
@Transactional
public class BodyCharacteristicsServiceImpl implements BodyCharacteristicsService {

    private final Logger log = LoggerFactory.getLogger(BodyCharacteristicsServiceImpl.class);

    private final BodyCharacteristicsRepository bodyCharacteristicsRepository;

    private final BodyCharacteristicsMapper bodyCharacteristicsMapper;

    public BodyCharacteristicsServiceImpl(BodyCharacteristicsRepository bodyCharacteristicsRepository, BodyCharacteristicsMapper bodyCharacteristicsMapper) {
        this.bodyCharacteristicsRepository = bodyCharacteristicsRepository;
        this.bodyCharacteristicsMapper = bodyCharacteristicsMapper;
    }

    @Override
    public BodyCharacteristicsDTO save(BodyCharacteristicsDTO bodyCharacteristicsDTO) {
        log.debug("Request to save BodyCharacteristics : {}", bodyCharacteristicsDTO);
        BodyCharacteristics bodyCharacteristics = bodyCharacteristicsMapper.toEntity(bodyCharacteristicsDTO);
        bodyCharacteristics = bodyCharacteristicsRepository.save(bodyCharacteristics);
        return bodyCharacteristicsMapper.toDto(bodyCharacteristics);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BodyCharacteristicsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BodyCharacteristics");
        return bodyCharacteristicsRepository.findAll(pageable)
            .map(bodyCharacteristicsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BodyCharacteristicsDTO> findOne(Long id) {
        log.debug("Request to get BodyCharacteristics : {}", id);
        return bodyCharacteristicsRepository.findById(id)
            .map(bodyCharacteristicsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BodyCharacteristics : {}", id);
        bodyCharacteristicsRepository.deleteById(id);
    }
}
