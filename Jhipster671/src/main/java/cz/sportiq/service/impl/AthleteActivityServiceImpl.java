package cz.sportiq.service.impl;

import cz.sportiq.domain.*;
import cz.sportiq.repository.ActivityRepository;
import cz.sportiq.repository.ActivityResultRepository;
import cz.sportiq.repository.AthleteActivityRepository;
import cz.sportiq.service.AthleteActivityResultService;
import cz.sportiq.service.AthleteActivityService;
import cz.sportiq.service.dto.AthleteActivityDTO;
import cz.sportiq.service.mapper.AthleteActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AthleteActivity}.
 */
@Service
@Transactional
public class AthleteActivityServiceImpl implements AthleteActivityService {

    private final Logger log = LoggerFactory.getLogger(AthleteActivityServiceImpl.class);

    private final AthleteActivityRepository athleteActivityRepository;

    private final AthleteActivityMapper athleteActivityMapper;

    private final ActivityRepository activityRepository;

    private final ActivityResultRepository activityResultRepository;

    private final AthleteActivityResultService athleteActivityResultService;

    public AthleteActivityServiceImpl(AthleteActivityRepository athleteActivityRepository, AthleteActivityMapper athleteActivityMapper, ActivityRepository activityRepository, ActivityResultRepository activityResultRepository, AthleteActivityResultService athleteActivityResultService) {
        this.athleteActivityRepository = athleteActivityRepository;
        this.athleteActivityMapper = athleteActivityMapper;
        this.activityRepository = activityRepository;
        this.activityResultRepository = activityResultRepository;
        this.athleteActivityResultService = athleteActivityResultService;
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
        return result;
    }

    /**
     * Get all the athleteActivities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
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
     * @param id the id of the entity.
     * @return the entity.
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
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AthleteActivity : {}", id);
        athleteActivityRepository.deleteById(id);
    }

    @Override
    public AthleteActivityDTO findByActivityIdAndAthleteWorkoutId(Long activityId, Long athleteWorkoutId) {
        Optional<AthleteActivity> athleteActivity = athleteActivityRepository.findByActivityIdAndAthleteWorkoutId(activityId, athleteWorkoutId);
        if(athleteActivity.isPresent()) {
            return athleteActivityMapper.toDto(athleteActivity.get());
        } else {
            AthleteActivityDTO athleteActivityDTO = new AthleteActivityDTO();
            athleteActivityDTO.setActivityId(activityId);
            athleteActivityDTO.setAthleteWorkoutId(athleteWorkoutId);
            return saveComplete(athleteActivityDTO);
        }
    }

    /**
     * Create a complete athleteActivity.
     *
     * @param athleteActivityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AthleteActivityDTO saveComplete(AthleteActivityDTO athleteActivityDTO) {
        log.debug("Request to save AthleteActivity : {}", athleteActivityDTO);

        AthleteActivity athleteActivity = athleteActivityMapper.toEntity(athleteActivityDTO);
        if(athleteActivityDTO.getId() == null) {
            Optional<Activity> activityOptional = activityRepository.findOneWithEagerRelationships(athleteActivity.getActivity().getId());
            if (activityOptional.isPresent()) {
                Activity activity = activityOptional.get();
                for (ActivityResult activityResult : activity.getActivityResults()) {

                    AthleteActivityResult athleteActivityResult = new AthleteActivityResult();
                    athleteActivityResult.setActivityResult(activityResult);
                    athleteActivityResult.setAthleteActivity(athleteActivity);

                    for(ActivityResultSplit activityResultSplit : activityResult.getResultSplits()) {

                        AthleteActivityResultSplit athleteActivityResultSplit = new AthleteActivityResultSplit();
                        athleteActivityResultSplit.setActivityResultSplit(activityResultSplit);
                        athleteActivityResultSplit.setAthleteActivityResult(athleteActivityResult);

                        athleteActivityResult.getAthleteActivityResultSplits().add(athleteActivityResultSplit);
                    }

                    athleteActivity.getAthleteActivityResults().add(athleteActivityResult);
                }
            }
        }

        athleteActivity = athleteActivityRepository.save(athleteActivity);
        AthleteActivityDTO result = athleteActivityMapper.toDto(athleteActivity);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public AthleteActivity findOrCreateAthleteActivity(Activity activity, AthleteWorkout athleteWorkout) {

        AthleteActivity athleteActivity;
        Optional<AthleteActivity> athleteActivityOptional = athleteActivityRepository.findByActivityIdAndAthleteWorkoutId(activity.getId(), athleteWorkout.getId());
        if (athleteActivityOptional.isPresent()) {
            athleteActivity = athleteActivityOptional.get();
        } else {
            athleteActivity = new AthleteActivity();
            athleteActivity.setActivity(activity);
            athleteActivity.setAthleteWorkout(athleteWorkout);
        }

        athleteActivity = athleteActivityRepository.saveAndFlush(athleteActivity);

        for(ActivityResult activityResult: activity.getActivityResults()) {
            AthleteActivityResult athleteActivityResult =
                athleteActivityResultService.findOrCreateAthleteActivityResult(activityResult, athleteActivity);
            athleteActivity.addAthleteActivityResults(athleteActivityResult);
        }
        return athleteActivityRepository.save(athleteActivity);
    }
}
