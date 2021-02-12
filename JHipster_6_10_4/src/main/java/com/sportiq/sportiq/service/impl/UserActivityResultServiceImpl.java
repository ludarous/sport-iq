package com.sportiq.sportiq.service.impl;

import com.sportiq.sportiq.service.UserActivityResultService;
import com.sportiq.sportiq.domain.UserActivityResult;
import com.sportiq.sportiq.repository.UserActivityResultRepository;
import com.sportiq.sportiq.service.dto.UserActivityResultDTO;
import com.sportiq.sportiq.service.mapper.UserActivityResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserActivityResult}.
 */
@Service
@Transactional
public class UserActivityResultServiceImpl implements UserActivityResultService {

    private final Logger log = LoggerFactory.getLogger(UserActivityResultServiceImpl.class);

    private final UserActivityResultRepository userActivityResultRepository;

    private final UserActivityResultMapper userActivityResultMapper;

    public UserActivityResultServiceImpl(UserActivityResultRepository userActivityResultRepository, UserActivityResultMapper userActivityResultMapper) {
        this.userActivityResultRepository = userActivityResultRepository;
        this.userActivityResultMapper = userActivityResultMapper;
    }

    @Override
    public UserActivityResultDTO save(UserActivityResultDTO userActivityResultDTO) {
        log.debug("Request to save UserActivityResult : {}", userActivityResultDTO);
        UserActivityResult userActivityResult = userActivityResultMapper.toEntity(userActivityResultDTO);
        userActivityResult = userActivityResultRepository.save(userActivityResult);
        return userActivityResultMapper.toDto(userActivityResult);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserActivityResultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserActivityResults");
        return userActivityResultRepository.findAll(pageable)
            .map(userActivityResultMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserActivityResultDTO> findOne(Long id) {
        log.debug("Request to get UserActivityResult : {}", id);
        return userActivityResultRepository.findById(id)
            .map(userActivityResultMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserActivityResult : {}", id);
        userActivityResultRepository.deleteById(id);
    }
}
