package cz.sportiq.service.impl;

import cz.sportiq.service.ActivityCategoryService;
import cz.sportiq.domain.ActivityCategory;
import cz.sportiq.repository.ActivityCategoryRepository;
import cz.sportiq.repository.search.ActivityCategorySearchRepository;
import cz.sportiq.service.dto.ActivityCategoryDTO;
import cz.sportiq.service.mapper.ActivityCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ActivityCategory}.
 */
@Service
@Transactional
public class ActivityCategoryServiceImpl implements ActivityCategoryService {

    private final Logger log = LoggerFactory.getLogger(ActivityCategoryServiceImpl.class);

    private final ActivityCategoryRepository activityCategoryRepository;

    private final ActivityCategoryMapper activityCategoryMapper;

    private final ActivityCategorySearchRepository activityCategorySearchRepository;

    public ActivityCategoryServiceImpl(ActivityCategoryRepository activityCategoryRepository, ActivityCategoryMapper activityCategoryMapper, ActivityCategorySearchRepository activityCategorySearchRepository) {
        this.activityCategoryRepository = activityCategoryRepository;
        this.activityCategoryMapper = activityCategoryMapper;
        this.activityCategorySearchRepository = activityCategorySearchRepository;
    }

    /**
     * Save a activityCategory.
     *
     * @param activityCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ActivityCategoryDTO save(ActivityCategoryDTO activityCategoryDTO) {
        log.debug("Request to save ActivityCategory : {}", activityCategoryDTO);
        ActivityCategory activityCategory = activityCategoryMapper.toEntity(activityCategoryDTO);
        activityCategory = activityCategoryRepository.save(activityCategory);
        ActivityCategoryDTO result = activityCategoryMapper.toDto(activityCategory);
        activityCategorySearchRepository.save(activityCategory);
        return result;
    }

    /**
     * Get all the activityCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ActivityCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ActivityCategories");
        return activityCategoryRepository.findAll(pageable)
            .map(activityCategoryMapper::toDto);
    }


    /**
     * Get one activityCategory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ActivityCategoryDTO> findOne(Long id) {
        log.debug("Request to get ActivityCategory : {}", id);
        return activityCategoryRepository.findById(id)
            .map(activityCategoryMapper::toDto);
    }

    /**
     * Delete the activityCategory by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActivityCategory : {}", id);
        activityCategoryRepository.deleteById(id);
        activityCategorySearchRepository.deleteById(id);
    }

    /**
     * Search for the activityCategory corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ActivityCategoryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ActivityCategories for query {}", query);
        return activityCategorySearchRepository.search(queryStringQuery(query), pageable)
            .map(activityCategoryMapper::toDto);
    }
}
