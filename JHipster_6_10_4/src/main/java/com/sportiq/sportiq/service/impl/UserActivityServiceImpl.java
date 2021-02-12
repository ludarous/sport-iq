package com.sportiq.sportiq.service.impl;

import com.sportiq.sportiq.service.UserActivityService;
import com.sportiq.sportiq.domain.UserActivity;
import com.sportiq.sportiq.repository.UserActivityRepository;
import com.sportiq.sportiq.service.dto.UserActivityDTO;
import com.sportiq.sportiq.service.mapper.UserActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserActivity}.
 */
@Service
@Transactional
public class UserActivityServiceImpl implements UserActivityService {

    private final Logger log = LoggerFactory.getLogger(UserActivityServiceImpl.class);

    private final UserActivityRepository userActivityRepository;

    private final UserActivityMapper userActivityMapper;

    public UserActivityServiceImpl(UserActivityRepository userActivityRepository, UserActivityMapper userActivityMapper) {
        this.userActivityRepository = userActivityRepository;
        this.userActivityMapper = userActivityMapper;
    }

    @Override
    public UserActivityDTO save(UserActivityDTO userActivityDTO) {
        log.debug("Request to save UserActivity : {}", userActivityDTO);
        UserActivity userActivity = userActivityMapper.toEntity(userActivityDTO);
        userActivity = userActivityRepository.save(userActivity);
        return userActivityMapper.toDto(userActivity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserActivityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserActivities");
        return userActivityRepository.findAll(pageable)
            .map(userActivityMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserActivityDTO> findOne(Long id) {
        log.debug("Request to get UserActivity : {}", id);
        return userActivityRepository.findById(id)
            .map(userActivityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserActivity : {}", id);
        userActivityRepository.deleteById(id);
    }
}
