package com.sportiq.sportiq.service.impl;

import com.sportiq.sportiq.service.UserEventService;
import com.sportiq.sportiq.domain.UserEvent;
import com.sportiq.sportiq.repository.UserEventRepository;
import com.sportiq.sportiq.service.dto.UserEventDTO;
import com.sportiq.sportiq.service.mapper.UserEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserEvent}.
 */
@Service
@Transactional
public class UserEventServiceImpl implements UserEventService {

    private final Logger log = LoggerFactory.getLogger(UserEventServiceImpl.class);

    private final UserEventRepository userEventRepository;

    private final UserEventMapper userEventMapper;

    public UserEventServiceImpl(UserEventRepository userEventRepository, UserEventMapper userEventMapper) {
        this.userEventRepository = userEventRepository;
        this.userEventMapper = userEventMapper;
    }

    @Override
    public UserEventDTO save(UserEventDTO userEventDTO) {
        log.debug("Request to save UserEvent : {}", userEventDTO);
        UserEvent userEvent = userEventMapper.toEntity(userEventDTO);
        userEvent = userEventRepository.save(userEvent);
        return userEventMapper.toDto(userEvent);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserEvents");
        return userEventRepository.findAll(pageable)
            .map(userEventMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserEventDTO> findOne(Long id) {
        log.debug("Request to get UserEvent : {}", id);
        return userEventRepository.findById(id)
            .map(userEventMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserEvent : {}", id);
        userEventRepository.deleteById(id);
    }
}
