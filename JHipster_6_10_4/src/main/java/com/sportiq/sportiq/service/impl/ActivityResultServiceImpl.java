package com.sportiq.sportiq.service.impl;

import com.sportiq.sportiq.service.ActivityResultService;
import com.sportiq.sportiq.domain.ActivityResult;
import com.sportiq.sportiq.repository.ActivityResultRepository;
import com.sportiq.sportiq.service.dto.ActivityResultDTO;
import com.sportiq.sportiq.service.mapper.ActivityResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ActivityResult}.
 */
@Service
@Transactional
public class ActivityResultServiceImpl implements ActivityResultService {

    private final Logger log = LoggerFactory.getLogger(ActivityResultServiceImpl.class);

    private final ActivityResultRepository activityResultRepository;

    private final ActivityResultMapper activityResultMapper;

    public ActivityResultServiceImpl(ActivityResultRepository activityResultRepository, ActivityResultMapper activityResultMapper) {
        this.activityResultRepository = activityResultRepository;
        this.activityResultMapper = activityResultMapper;
    }

    @Override
    public ActivityResultDTO save(ActivityResultDTO activityResultDTO) {
        log.debug("Request to save ActivityResult : {}", activityResultDTO);
        ActivityResult activityResult = activityResultMapper.toEntity(activityResultDTO);
        activityResult = activityResultRepository.save(activityResult);
        return activityResultMapper.toDto(activityResult);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityResultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ActivityResults");
        return activityResultRepository.findAll(pageable)
            .map(activityResultMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ActivityResultDTO> findOne(Long id) {
        log.debug("Request to get ActivityResult : {}", id);
        return activityResultRepository.findById(id)
            .map(activityResultMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActivityResult : {}", id);
        activityResultRepository.deleteById(id);
    }
}
