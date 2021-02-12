package com.sportiq.sportiq.service.impl;

import com.sportiq.sportiq.service.UserActivityResultSplitService;
import com.sportiq.sportiq.domain.UserActivityResultSplit;
import com.sportiq.sportiq.repository.UserActivityResultSplitRepository;
import com.sportiq.sportiq.service.dto.UserActivityResultSplitDTO;
import com.sportiq.sportiq.service.mapper.UserActivityResultSplitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserActivityResultSplit}.
 */
@Service
@Transactional
public class UserActivityResultSplitServiceImpl implements UserActivityResultSplitService {

    private final Logger log = LoggerFactory.getLogger(UserActivityResultSplitServiceImpl.class);

    private final UserActivityResultSplitRepository userActivityResultSplitRepository;

    private final UserActivityResultSplitMapper userActivityResultSplitMapper;

    public UserActivityResultSplitServiceImpl(UserActivityResultSplitRepository userActivityResultSplitRepository, UserActivityResultSplitMapper userActivityResultSplitMapper) {
        this.userActivityResultSplitRepository = userActivityResultSplitRepository;
        this.userActivityResultSplitMapper = userActivityResultSplitMapper;
    }

    @Override
    public UserActivityResultSplitDTO save(UserActivityResultSplitDTO userActivityResultSplitDTO) {
        log.debug("Request to save UserActivityResultSplit : {}", userActivityResultSplitDTO);
        UserActivityResultSplit userActivityResultSplit = userActivityResultSplitMapper.toEntity(userActivityResultSplitDTO);
        userActivityResultSplit = userActivityResultSplitRepository.save(userActivityResultSplit);
        return userActivityResultSplitMapper.toDto(userActivityResultSplit);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserActivityResultSplitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserActivityResultSplits");
        return userActivityResultSplitRepository.findAll(pageable)
            .map(userActivityResultSplitMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserActivityResultSplitDTO> findOne(Long id) {
        log.debug("Request to get UserActivityResultSplit : {}", id);
        return userActivityResultSplitRepository.findById(id)
            .map(userActivityResultSplitMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserActivityResultSplit : {}", id);
        userActivityResultSplitRepository.deleteById(id);
    }
}
