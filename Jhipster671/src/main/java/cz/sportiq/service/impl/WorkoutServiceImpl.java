package cz.sportiq.service.impl;

import cz.sportiq.service.WorkoutService;
import cz.sportiq.domain.Workout;
import cz.sportiq.repository.WorkoutRepository;
import cz.sportiq.service.dto.WorkoutDTO;
import cz.sportiq.service.mapper.WorkoutMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Workout}.
 */
@Service
@Transactional
public class WorkoutServiceImpl implements WorkoutService {

    private final Logger log = LoggerFactory.getLogger(WorkoutServiceImpl.class);

    private final WorkoutRepository workoutRepository;

    private final WorkoutMapper workoutMapper;

    public WorkoutServiceImpl(WorkoutRepository workoutRepository, WorkoutMapper workoutMapper) {
        this.workoutRepository = workoutRepository;
        this.workoutMapper = workoutMapper;
    }

    /**
     * Save a workout.
     *
     * @param workoutDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WorkoutDTO save(WorkoutDTO workoutDTO) {
        log.debug("Request to save Workout : {}", workoutDTO);
        Workout workout = workoutMapper.toEntity(workoutDTO);
        workout = workoutRepository.save(workout);
        return workoutMapper.toDto(workout);
    }

    /**
     * Get all the workouts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WorkoutDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Workouts");
        return workoutRepository.findAll(pageable)
            .map(workoutMapper::toDto);
    }

    /**
     * Get all the workouts with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<WorkoutDTO> findAllWithEagerRelationships(Pageable pageable) {
        return workoutRepository.findAllWithEagerRelationships(pageable).map(workoutMapper::toDto);
    }

    /**
     * Get one workout by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<WorkoutDTO> findOne(Long id) {
        log.debug("Request to get Workout : {}", id);
        return workoutRepository.findOneWithEagerRelationships(id)
            .map(workoutMapper::toDto);
    }

    /**
     * Delete the workout by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Workout : {}", id);
        workoutRepository.deleteById(id);
    }
}
