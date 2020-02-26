package cz.sportiq.service.impl;

import cz.sportiq.service.ActivityResultService;
import cz.sportiq.domain.ActivityResult;
import cz.sportiq.repository.ActivityResultRepository;
import cz.sportiq.service.dto.ActivityResultDTO;
import cz.sportiq.service.mapper.ActivityResultMapper;
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

    /**
     * Save a activityResult.
     *
     * @param activityResultDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ActivityResultDTO save(ActivityResultDTO activityResultDTO) {
        log.debug("Request to save ActivityResult : {}", activityResultDTO);
        ActivityResult activityResult = activityResultMapper.toEntity(activityResultDTO);
        activityResult = activityResultRepository.save(activityResult);
        return activityResultMapper.toDto(activityResult);
    }

    /**
     * Get all the activityResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ActivityResultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ActivityResults");
        return activityResultRepository.findAll(pageable)
            .map(activityResultMapper::toDto);
    }

    /**
     * Get one activityResult by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ActivityResultDTO> findOne(Long id) {
        log.debug("Request to get ActivityResult : {}", id);
        return activityResultRepository.findById(id)
            .map(activityResultMapper::toDto);
    }

    /**
     * Delete the activityResult by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActivityResult : {}", id);
        activityResultRepository.deleteById(id);
    }
}
