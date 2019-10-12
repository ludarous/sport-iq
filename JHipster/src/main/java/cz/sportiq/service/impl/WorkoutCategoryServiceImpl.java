package cz.sportiq.service.impl;

import cz.sportiq.service.WorkoutCategoryService;
import cz.sportiq.domain.WorkoutCategory;
import cz.sportiq.repository.WorkoutCategoryRepository;
import cz.sportiq.repository.search.WorkoutCategorySearchRepository;
import cz.sportiq.service.dto.WorkoutCategoryDTO;
import cz.sportiq.service.mapper.WorkoutCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing WorkoutCategory.
 */
@Service
@Transactional
public class WorkoutCategoryServiceImpl implements WorkoutCategoryService {

    private final Logger log = LoggerFactory.getLogger(WorkoutCategoryServiceImpl.class);

    private final WorkoutCategoryRepository workoutCategoryRepository;

    private final WorkoutCategoryMapper workoutCategoryMapper;

    private final WorkoutCategorySearchRepository workoutCategorySearchRepository;

    public WorkoutCategoryServiceImpl(WorkoutCategoryRepository workoutCategoryRepository, WorkoutCategoryMapper workoutCategoryMapper, WorkoutCategorySearchRepository workoutCategorySearchRepository) {
        this.workoutCategoryRepository = workoutCategoryRepository;
        this.workoutCategoryMapper = workoutCategoryMapper;
        this.workoutCategorySearchRepository = workoutCategorySearchRepository;
    }

    /**
     * Save a workoutCategory.
     *
     * @param workoutCategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public WorkoutCategoryDTO save(WorkoutCategoryDTO workoutCategoryDTO) {
        log.debug("Request to save WorkoutCategory : {}", workoutCategoryDTO);
        WorkoutCategory workoutCategory = workoutCategoryMapper.toEntity(workoutCategoryDTO);
        workoutCategory = workoutCategoryRepository.save(workoutCategory);
        WorkoutCategoryDTO result = workoutCategoryMapper.toDto(workoutCategory);
        workoutCategorySearchRepository.save(workoutCategory);
        return result;
    }

    /**
     * Get all the workoutCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WorkoutCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkoutCategories");
        return workoutCategoryRepository.findAll(pageable)
            .map(workoutCategoryMapper::toDto);
    }


    /**
     * Get one workoutCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WorkoutCategoryDTO> findOne(Long id) {
        log.debug("Request to get WorkoutCategory : {}", id);
        return workoutCategoryRepository.findById(id)
            .map(workoutCategoryMapper::toDto);
    }

    /**
     * Delete the workoutCategory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkoutCategory : {}", id);
        workoutCategoryRepository.deleteById(id);
        workoutCategorySearchRepository.deleteById(id);
    }

    /**
     * Search for the workoutCategory corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WorkoutCategoryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of WorkoutCategories for query {}", query);
        return workoutCategorySearchRepository.search(queryStringQuery(query), pageable)
            .map(workoutCategoryMapper::toDto);
    }
}
