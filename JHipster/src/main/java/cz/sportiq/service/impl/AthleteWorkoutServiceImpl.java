package cz.sportiq.service.impl;

import cz.sportiq.service.AthleteWorkoutService;
import cz.sportiq.domain.AthleteWorkout;
import cz.sportiq.repository.AthleteWorkoutRepository;
import cz.sportiq.repository.search.AthleteWorkoutSearchRepository;
import cz.sportiq.service.dto.AthleteWorkoutDTO;
import cz.sportiq.service.mapper.AthleteWorkoutMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AthleteWorkout.
 */
@Service
@Transactional
public class AthleteWorkoutServiceImpl implements AthleteWorkoutService {

    private final Logger log = LoggerFactory.getLogger(AthleteWorkoutServiceImpl.class);

    private final AthleteWorkoutRepository athleteWorkoutRepository;

    private final AthleteWorkoutMapper athleteWorkoutMapper;

    private final AthleteWorkoutSearchRepository athleteWorkoutSearchRepository;

    public AthleteWorkoutServiceImpl(AthleteWorkoutRepository athleteWorkoutRepository, AthleteWorkoutMapper athleteWorkoutMapper, AthleteWorkoutSearchRepository athleteWorkoutSearchRepository) {
        this.athleteWorkoutRepository = athleteWorkoutRepository;
        this.athleteWorkoutMapper = athleteWorkoutMapper;
        this.athleteWorkoutSearchRepository = athleteWorkoutSearchRepository;
    }

    /**
     * Save a athleteWorkout.
     *
     * @param athleteWorkoutDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AthleteWorkoutDTO save(AthleteWorkoutDTO athleteWorkoutDTO) {
        log.debug("Request to save AthleteWorkout : {}", athleteWorkoutDTO);
        AthleteWorkout athleteWorkout = athleteWorkoutMapper.toEntity(athleteWorkoutDTO);
        athleteWorkout = athleteWorkoutRepository.save(athleteWorkout);
        AthleteWorkoutDTO result = athleteWorkoutMapper.toDto(athleteWorkout);
        athleteWorkoutSearchRepository.save(athleteWorkout);
        return result;
    }

    /**
     * Get all the athleteWorkouts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AthleteWorkoutDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AthleteWorkouts");
        return athleteWorkoutRepository.findAll(pageable)
            .map(athleteWorkoutMapper::toDto);
    }


    /**
     * Get one athleteWorkout by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AthleteWorkoutDTO> findOne(Long id) {
        log.debug("Request to get AthleteWorkout : {}", id);
        return athleteWorkoutRepository.findById(id)
            .map(athleteWorkoutMapper::toDto);
    }

    /**
     * Delete the athleteWorkout by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AthleteWorkout : {}", id);
        athleteWorkoutRepository.deleteById(id);
        athleteWorkoutSearchRepository.deleteById(id);
    }

    /**
     * Search for the athleteWorkout corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AthleteWorkoutDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AthleteWorkouts for query {}", query);
        return athleteWorkoutSearchRepository.search(queryStringQuery(query), pageable)
            .map(athleteWorkoutMapper::toDto);
    }

    @Override
    public AthleteWorkoutDTO findByWorkoutIdAndAthleteEventId(Long workoutId, Long athleteEventId) {
        Optional<AthleteWorkout> athleteWorkout = athleteWorkoutRepository.findByWorkoutIdAndAthleteEventId(workoutId, athleteEventId);
        if(athleteWorkout.isPresent()) {
            return athleteWorkoutMapper.toDto(athleteWorkout.get());
        } else {
            AthleteWorkoutDTO athleteWorkoutDTO = new AthleteWorkoutDTO();
            athleteWorkoutDTO.setWorkoutId(workoutId);
            athleteWorkoutDTO.setAthleteEventId(athleteEventId);
            return save(athleteWorkoutDTO);
        }
    }
}
