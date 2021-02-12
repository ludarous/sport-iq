package com.sportiq.sportiq.service.impl;

import com.sportiq.sportiq.service.UnitService;
import com.sportiq.sportiq.domain.Unit;
import com.sportiq.sportiq.repository.UnitRepository;
import com.sportiq.sportiq.service.dto.UnitDTO;
import com.sportiq.sportiq.service.mapper.UnitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Unit}.
 */
@Service
@Transactional
public class UnitServiceImpl implements UnitService {

    private final Logger log = LoggerFactory.getLogger(UnitServiceImpl.class);

    private final UnitRepository unitRepository;

    private final UnitMapper unitMapper;

    public UnitServiceImpl(UnitRepository unitRepository, UnitMapper unitMapper) {
        this.unitRepository = unitRepository;
        this.unitMapper = unitMapper;
    }

    @Override
    public UnitDTO save(UnitDTO unitDTO) {
        log.debug("Request to save Unit : {}", unitDTO);
        Unit unit = unitMapper.toEntity(unitDTO);
        unit = unitRepository.save(unit);
        return unitMapper.toDto(unit);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UnitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Units");
        return unitRepository.findAll(pageable)
            .map(unitMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UnitDTO> findOne(Long id) {
        log.debug("Request to get Unit : {}", id);
        return unitRepository.findById(id)
            .map(unitMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Unit : {}", id);
        unitRepository.deleteById(id);
    }
}
