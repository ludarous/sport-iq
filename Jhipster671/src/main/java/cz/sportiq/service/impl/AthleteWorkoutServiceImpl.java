package cz.sportiq.service.impl;

import cz.sportiq.service.AthleteWorkoutService;
import cz.sportiq.domain.AthleteWorkout;
import cz.sportiq.repository.AthleteWorkoutRepository;
import cz.sportiq.service.dto.AthleteWorkoutDTO;
import cz.sportiq.service.mapper.AthleteWorkoutMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AthleteWorkout}.
 */
@Service
@Transactional
public class AthleteWorkoutServiceImpl implements AthleteWorkoutService {

    private final Logger log = LoggerFactory.getLogger(AthleteWorkoutServiceImpl.class);

    private final AthleteWorkoutRepository athleteWorkoutRepository;

    private final AthleteWorkoutMapper athleteWorkoutMapper;

    public AthleteWorkoutServiceImpl(AthleteWorkoutRepository athleteWorkoutRepository, AthleteWorkoutMapper athleteWorkoutMapper) {
        this.athleteWorkoutRepository = athleteWorkoutRepository;
        this.athleteWorkoutMapper = athleteWorkoutMapper;
    }

    /**
     * Save a athleteWorkout.
     *
     * @param athleteWorkoutDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AthleteWorkoutDTO save(AthleteWorkoutDTO athleteWorkoutDTO) {
        log.debug("Request to save AthleteWorkout : {}", athleteWorkoutDTO);
        AthleteWorkout athleteWorkout = athleteWorkoutMapper.toEntity(athleteWorkoutDTO);
        athleteWorkout = athleteWorkoutRepository.save(athleteWorkout);
        return athleteWorkoutMapper.toDto(athleteWorkout);
    }

    /**
     * Get all the athleteWorkouts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
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
     * @param id the id of the entity.
     * @return the entity.
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
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AthleteWorkout : {}", id);
        athleteWorkoutRepository.deleteById(id);
    }
}
