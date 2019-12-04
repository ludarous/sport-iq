package cz.sportiq.service.impl;

import cz.sportiq.service.AthleteActivityService;
import cz.sportiq.domain.AthleteActivity;
import cz.sportiq.repository.AthleteActivityRepository;
import cz.sportiq.repository.search.AthleteActivitySearchRepository;
import cz.sportiq.service.dto.AthleteActivityDTO;
import cz.sportiq.service.dto.AthleteWorkoutDTO;
import cz.sportiq.service.mapper.AthleteActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AthleteActivity.
 */
@Service
@Transactional
public class AthleteActivityServiceImpl implements AthleteActivityService {

    private final Logger log = LoggerFactory.getLogger(AthleteActivityServiceImpl.class);

    private final AthleteActivityRepository athleteActivityRepository;

    private final AthleteActivityMapper athleteActivityMapper;

    private final AthleteActivitySearchRepository athleteActivitySearchRepository;

    public AthleteActivityServiceImpl(AthleteActivityRepository athleteActivityRepository, AthleteActivityMapper athleteActivityMapper, AthleteActivitySearchRepository athleteActivitySearchRepository) {
        this.athleteActivityRepository = athleteActivityRepository;
        this.athleteActivityMapper = athleteActivityMapper;
        this.athleteActivitySearchRepository = athleteActivitySearchRepository;
    }

    /**
     * Save a athleteActivity.
     *
     * @param athleteActivityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AthleteActivityDTO save(AthleteActivityDTO athleteActivityDTO) {
        log.debug("Request to save AthleteActivity : {}", athleteActivityDTO);
        AthleteActivity athleteActivity = athleteActivityMapper.toEntity(athleteActivityDTO);
        athleteActivity = athleteActivityRepository.save(athleteActivity);
        AthleteActivityDTO result = athleteActivityMapper.toDto(athleteActivity);
        athleteActivitySearchRepository.save(athleteActivity);
        return result;
    }

    /**
     * Get all the athleteActivities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AthleteActivityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AthleteActivities");
        return athleteActivityRepository.findAll(pageable)
            .map(athleteActivityMapper::toDto);
    }


    /**
     * Get one athleteActivity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AthleteActivityDTO> findOne(Long id) {
        log.debug("Request to get AthleteActivity : {}", id);
        return athleteActivityRepository.findById(id)
            .map(athleteActivityMapper::toDto);
    }

    /**
     * Delete the athleteActivity by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AthleteActivity : {}", id);
        athleteActivityRepository.deleteById(id);
        athleteActivitySearchRepository.deleteById(id);
    }

    /**
     * Search for the athleteActivity corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AthleteActivityDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AthleteActivities for query {}", query);
        return athleteActivitySearchRepository.search(queryStringQuery(query), pageable)
            .map(athleteActivityMapper::toDto);
    }

    @Override
    public Optional<AthleteActivityDTO> findByActivityIdAndAthleteWorkoutId(Long activityId, Long athleteWorkoutId) {
        return athleteActivityRepository.findByActivityIdAndAthleteWorkoutId(activityId, athleteWorkoutId)
            .map(athleteActivityMapper::toDto);
    }
}
