package com.sportiq.sportiq.service.impl;

import com.sportiq.sportiq.service.EventLocationService;
import com.sportiq.sportiq.domain.EventLocation;
import com.sportiq.sportiq.repository.EventLocationRepository;
import com.sportiq.sportiq.service.dto.EventLocationDTO;
import com.sportiq.sportiq.service.mapper.EventLocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EventLocation}.
 */
@Service
@Transactional
public class EventLocationServiceImpl implements EventLocationService {

    private final Logger log = LoggerFactory.getLogger(EventLocationServiceImpl.class);

    private final EventLocationRepository eventLocationRepository;

    private final EventLocationMapper eventLocationMapper;

    public EventLocationServiceImpl(EventLocationRepository eventLocationRepository, EventLocationMapper eventLocationMapper) {
        this.eventLocationRepository = eventLocationRepository;
        this.eventLocationMapper = eventLocationMapper;
    }

    @Override
    public EventLocationDTO save(EventLocationDTO eventLocationDTO) {
        log.debug("Request to save EventLocation : {}", eventLocationDTO);
        EventLocation eventLocation = eventLocationMapper.toEntity(eventLocationDTO);
        eventLocation = eventLocationRepository.save(eventLocation);
        return eventLocationMapper.toDto(eventLocation);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EventLocationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventLocations");
        return eventLocationRepository.findAll(pageable)
            .map(eventLocationMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<EventLocationDTO> findOne(Long id) {
        log.debug("Request to get EventLocation : {}", id);
        return eventLocationRepository.findById(id)
            .map(eventLocationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventLocation : {}", id);
        eventLocationRepository.deleteById(id);
    }
}
