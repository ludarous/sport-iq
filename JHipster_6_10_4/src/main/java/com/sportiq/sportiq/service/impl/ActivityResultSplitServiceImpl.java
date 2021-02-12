package com.sportiq.sportiq.service.impl;

import com.sportiq.sportiq.service.ActivityResultSplitService;
import com.sportiq.sportiq.domain.ActivityResultSplit;
import com.sportiq.sportiq.repository.ActivityResultSplitRepository;
import com.sportiq.sportiq.service.dto.ActivityResultSplitDTO;
import com.sportiq.sportiq.service.mapper.ActivityResultSplitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ActivityResultSplit}.
 */
@Service
@Transactional
public class ActivityResultSplitServiceImpl implements ActivityResultSplitService {

    private final Logger log = LoggerFactory.getLogger(ActivityResultSplitServiceImpl.class);

    private final ActivityResultSplitRepository activityResultSplitRepository;

    private final ActivityResultSplitMapper activityResultSplitMapper;

    public ActivityResultSplitServiceImpl(ActivityResultSplitRepository activityResultSplitRepository, ActivityResultSplitMapper activityResultSplitMapper) {
        this.activityResultSplitRepository = activityResultSplitRepository;
        this.activityResultSplitMapper = activityResultSplitMapper;
    }

    @Override
    public ActivityResultSplitDTO save(ActivityResultSplitDTO activityResultSplitDTO) {
        log.debug("Request to save ActivityResultSplit : {}", activityResultSplitDTO);
        ActivityResultSplit activityResultSplit = activityResultSplitMapper.toEntity(activityResultSplitDTO);
        activityResultSplit = activityResultSplitRepository.save(activityResultSplit);
        return activityResultSplitMapper.toDto(activityResultSplit);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityResultSplitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ActivityResultSplits");
        return activityResultSplitRepository.findAll(pageable)
            .map(activityResultSplitMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ActivityResultSplitDTO> findOne(Long id) {
        log.debug("Request to get ActivityResultSplit : {}", id);
        return activityResultSplitRepository.findById(id)
            .map(activityResultSplitMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActivityResultSplit : {}", id);
        activityResultSplitRepository.deleteById(id);
    }
}
