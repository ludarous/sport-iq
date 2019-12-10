package cz.sportiq.service.impl.custom;

import cz.sportiq.domain.*;
import cz.sportiq.repository.*;
import cz.sportiq.service.dto.custom.*;
import cz.sportiq.service.impl.ActivityResultServiceImpl;
import cz.sportiq.service.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SummaryService {
    private final Logger log = LoggerFactory.getLogger(ActivityResultServiceImpl.class);

    private final AthleteEventRepository athleteEventRepository;

    private final AthleteEventMapper athleteEventMapper;

    private final EventRepository eventRepository;

    private final EventMapper eventMapper;

    private final WorkoutMapper workoutMapper;

    private final ActivityMapper activityMapper;

    private final ActivityResultMapper activityResultMapper;

    private final ActivityResultSplitMapper activityResultSplitMapper;

    private final AthleteWorkoutRepository athleteWorkoutRepository;

    private final AthleteWorkoutMapper athleteWorkoutMapper;

    private final AthleteActivityRepository athleteActivityRepository;

    private final AthleteActivityMapper athleteActivityMapper;

    private final AthleteActivityResultRepository athleteActivityResultRepository;

    private final AthleteActivityResultMapper athleteActivityResultMapper;

    private final AthleteActivityResultSplitRepository athleteActivityResultSplitRepository;

    private final AthleteActivityResultSplitMapper athleteActivityResultSplitMapper;

    public SummaryService(AthleteEventRepository athleteEventRepository, AthleteEventMapper athleteEventMapper, EventRepository eventRepository, EventMapper eventMapper, WorkoutMapper workoutMapper, ActivityMapper activityMapper, ActivityResultMapper activityResultMapper, ActivityResultSplitMapper activityResultSplitMapper, AthleteWorkoutRepository athleteWorkoutRepository, AthleteWorkoutMapper athleteWorkoutMapper, AthleteActivityRepository athleteActivityRepository, AthleteActivityMapper athleteActivityMapper, AthleteActivityResultRepository athleteActivityResultRepository, AthleteActivityResultMapper athleteActivityResultMapper, AthleteActivityResultSplitRepository athleteActivityResultSplitRepository, AthleteActivityResultSplitMapper athleteActivityResultSplitMapper) {
        this.athleteEventRepository = athleteEventRepository;
        this.athleteEventMapper = athleteEventMapper;
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.workoutMapper = workoutMapper;
        this.activityMapper = activityMapper;
        this.activityResultMapper = activityResultMapper;
        this.activityResultSplitMapper = activityResultSplitMapper;
        this.athleteWorkoutRepository = athleteWorkoutRepository;
        this.athleteWorkoutMapper = athleteWorkoutMapper;
        this.athleteActivityRepository = athleteActivityRepository;
        this.athleteActivityMapper = athleteActivityMapper;
        this.athleteActivityResultRepository = athleteActivityResultRepository;
        this.athleteActivityResultMapper = athleteActivityResultMapper;
        this.athleteActivityResultSplitRepository = athleteActivityResultSplitRepository;
        this.athleteActivityResultSplitMapper = athleteActivityResultSplitMapper;
    }

    public AthleteEventSummaryDTO getAthleteEventSummary(Long eventId, Long athleteId) {
        AthleteEventSummaryDTO athleteEventSummary = new AthleteEventSummaryDTO();

        Optional<AthleteEvent> athleteAthleteEventOptional = this.athleteEventRepository.findByEventIdAndAthleteId(eventId, athleteId);
        Optional<Event> eventOptional = this.eventRepository.findById(eventId);

        if(!athleteAthleteEventOptional.isPresent()) {
            log.warn("Cannot find athlete event for athlete {} and event {}", athleteId, eventId);
            return null;
        }

        if(!eventOptional.isPresent()) {
            log.warn("Cannot find event {}", eventId);
            return null;
        }

        Event event = eventOptional.get();

        athleteEventSummary.setAthleteEvent(athleteEventMapper.toDto(athleteAthleteEventOptional.get()));
        athleteEventSummary.setEvent(eventMapper.toDto(event));

        for(Workout workout : event.getTests()) {
            athleteEventSummary.getWorkoutSummaries().add(getAthleteWorkoutSummary(workout, athleteAthleteEventOptional.get(), athleteId));
        }



        return athleteEventSummary;
    }

    AthleteWorkoutSummaryDTO getAthleteWorkoutSummary(Workout workout, AthleteEvent athleteEvent, Long athleteId) {
        AthleteWorkoutSummaryDTO athleteWorkoutSummary = new AthleteWorkoutSummaryDTO();

        Optional<AthleteWorkout> athleteAthleteWorkoutOptional = this.athleteWorkoutRepository.findByWorkoutIdAndAthleteEventId(workout.getId(), athleteEvent.getId());

        if(!athleteAthleteWorkoutOptional.isPresent()) {
            log.warn("Cannot find athlete workout for athlete {} and workout {}", athleteId, workout.getId());
            return null;
        }

        athleteWorkoutSummary.setWorkout(workoutMapper.toDto(workout));
        athleteWorkoutSummary.setAthleteWorkout(athleteWorkoutMapper.toDto(athleteAthleteWorkoutOptional.get()));

        for(Activity activity : workout.getActivities()) {
            athleteWorkoutSummary.getActivitySummaries().add(getAthleteActivitySummary(activity, athleteAthleteWorkoutOptional.get(), athleteId));
        }

        return athleteWorkoutSummary;
    }

    private AthleteActivitySummaryDTO getAthleteActivitySummary(Activity activity, AthleteWorkout athleteWorkout, Long athleteId) {
        AthleteActivitySummaryDTO athleteActivitySummary = new AthleteActivitySummaryDTO();

        Optional<AthleteActivity> athleteAthleteActivityOptional = this.athleteActivityRepository.findByActivityIdAndAthleteWorkoutId(activity.getId(), athleteWorkout.getId());

        if(!athleteAthleteActivityOptional.isPresent()) {
            log.warn("Cannot find athlete activity for athlete {} and activity {}", athleteId, activity.getId());
            return null;
        }

        athleteActivitySummary.setActivity(activityMapper.toDto(activity));
        athleteActivitySummary.setAthleteActivity(athleteActivityMapper.toDto(athleteAthleteActivityOptional.get()));

        for(ActivityResult activityResult : activity.getActivityResults()) {
            athleteActivitySummary.getResultSummaries().add(getAthleteActivityResultSummary(activityResult, athleteAthleteActivityOptional.get(), athleteId));
        }

        return athleteActivitySummary;
    }

    private AthleteActivityResultSummaryDTO getAthleteActivityResultSummary(ActivityResult activityResult, AthleteActivity athleteActivity, Long athleteId) {
        AthleteActivityResultSummaryDTO athleteActivityResultSummary = new AthleteActivityResultSummaryDTO();

        Optional<AthleteActivityResult> athleteAthleteActivityResultOptional = this.athleteActivityResultRepository.findByActivityResultIdAndAthleteActivityId(activityResult.getId(), athleteActivity.getId());

        if(!athleteAthleteActivityResultOptional.isPresent()) {
            log.warn("Cannot find athlete activityResult for athlete {} and activityResult {}", athleteId, activityResult.getId());
            return null;
        }

        athleteActivityResultSummary.setActivityResult(activityResultMapper.toDto(activityResult));
        athleteActivityResultSummary.setAthleteActivityResult(athleteActivityResultMapper.toDto(athleteAthleteActivityResultOptional.get()));

        for(ActivityResultSplit activityResultSplit : activityResult.getResultSplits()) {
            athleteActivityResultSummary.getResultSplitSummaries().add(getAthleteActivityResultSplitResultSummary(activityResultSplit, athleteAthleteActivityResultOptional.get(), athleteId));
        }

        return athleteActivityResultSummary;
    }

    private AthleteActivityResultSplitSummaryDTO getAthleteActivityResultSplitResultSummary(ActivityResultSplit activityResultSplit, AthleteActivityResult athleteActivityResult, Long athleteId) {
        AthleteActivityResultSplitSummaryDTO athleteActivityResultSplitSummary = new AthleteActivityResultSplitSummaryDTO();

        Optional<AthleteActivityResultSplit> athleteAthleteActivityResultSplitOptional = this.athleteActivityResultSplitRepository.findByActivityResultSplitIdAndAthleteActivityResultId(activityResultSplit.getId(), athleteActivityResult.getId());

        if(!athleteAthleteActivityResultSplitOptional.isPresent()) {
            log.warn("Cannot find athlete activityResultSplit for athlete {} and activityResultSplit {}", athleteId, activityResultSplit.getId());
            return null;
        }

        athleteActivityResultSplitSummary.setActivityResultSplit(activityResultSplitMapper.toDto(activityResultSplit));
        athleteActivityResultSplitSummary.setAthleteActivityResultSplit(athleteActivityResultSplitMapper.toDto(athleteAthleteActivityResultSplitOptional.get()));

        StatsDTO stats = getStatsForActivityResultSplit(activityResultSplit, athleteActivityResult);

        return athleteActivityResultSplitSummary;
    }

    StatsDTO getStatsForActivityResultSplit(ActivityResultSplit activityResultSplit, AthleteActivityResult athleteActivityResult) {
        Event event = athleteActivityResult.getAthleteActivity().getAthleteWorkout().getAthleteEvent().getEvent();
        List<ActivityResultSplit> activityResultSplitsForEvent = athleteActivityResultSplitRepository.findActivityResultSplitsByEventId(activityResultSplit.getId(), event.getId());
        return new StatsDTO();
    }
}
