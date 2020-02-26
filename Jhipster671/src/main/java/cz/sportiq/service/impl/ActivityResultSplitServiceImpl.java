package cz.sportiq.service.impl;

import cz.sportiq.service.ActivityResultSplitService;
import cz.sportiq.domain.ActivityResultSplit;
import cz.sportiq.repository.ActivityResultSplitRepository;
import cz.sportiq.service.dto.ActivityResultSplitDTO;
import cz.sportiq.service.mapper.ActivityResultSplitMapper;
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

    /**
     * Save a activityResultSplit.
     *
     * @param activityResultSplitDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ActivityResultSplitDTO save(ActivityResultSplitDTO activityResultSplitDTO) {
        log.debug("Request to save ActivityResultSplit : {}", activityResultSplitDTO);
        ActivityResultSplit activityResultSplit = activityResultSplitMapper.toEntity(activityResultSplitDTO);
        activityResultSplit = activityResultSplitRepository.save(activityResultSplit);
        return activityResultSplitMapper.toDto(activityResultSplit);
    }

    /**
     * Get all the activityResultSplits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ActivityResultSplitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ActivityResultSplits");
        return activityResultSplitRepository.findAll(pageable)
            .map(activityResultSplitMapper::toDto);
    }

    /**
     * Get one activityResultSplit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ActivityResultSplitDTO> findOne(Long id) {
        log.debug("Request to get ActivityResultSplit : {}", id);
        return activityResultSplitRepository.findById(id)
            .map(activityResultSplitMapper::toDto);
    }

    /**
     * Delete the activityResultSplit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActivityResultSplit : {}", id);
        activityResultSplitRepository.deleteById(id);
    }
}
