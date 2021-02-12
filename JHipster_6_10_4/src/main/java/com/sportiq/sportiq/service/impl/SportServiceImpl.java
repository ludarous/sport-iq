package com.sportiq.sportiq.service.impl;

import com.sportiq.sportiq.service.SportService;
import com.sportiq.sportiq.domain.Sport;
import com.sportiq.sportiq.repository.SportRepository;
import com.sportiq.sportiq.service.dto.SportDTO;
import com.sportiq.sportiq.service.mapper.SportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Sport}.
 */
@Service
@Transactional
public class SportServiceImpl implements SportService {

    private final Logger log = LoggerFactory.getLogger(SportServiceImpl.class);

    private final SportRepository sportRepository;

    private final SportMapper sportMapper;

    public SportServiceImpl(SportRepository sportRepository, SportMapper sportMapper) {
        this.sportRepository = sportRepository;
        this.sportMapper = sportMapper;
    }

    @Override
    public SportDTO save(SportDTO sportDTO) {
        log.debug("Request to save Sport : {}", sportDTO);
        Sport sport = sportMapper.toEntity(sportDTO);
        sport = sportRepository.save(sport);
        return sportMapper.toDto(sport);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sports");
        return sportRepository.findAll(pageable)
            .map(sportMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SportDTO> findOne(Long id) {
        log.debug("Request to get Sport : {}", id);
        return sportRepository.findById(id)
            .map(sportMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sport : {}", id);
        sportRepository.deleteById(id);
    }
}
