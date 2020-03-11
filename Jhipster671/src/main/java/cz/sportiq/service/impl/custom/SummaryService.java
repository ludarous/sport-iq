package cz.sportiq.service.impl.custom;

import cz.sportiq.domain.*;
import cz.sportiq.domain.comparators.AthleteActivityResultCompareValueComparator;
import cz.sportiq.domain.comparators.AthleteActivityResultSplitsCompareValueComparator;
import cz.sportiq.domain.comparators.AthleteActivityResultSplitsValueComparator;
import cz.sportiq.domain.comparators.AthleteActivityResultValueComparator;
import cz.sportiq.domain.enumeration.ResultType;
import cz.sportiq.repository.*;
import cz.sportiq.service.dto.custom.*;
import cz.sportiq.service.impl.ActivityResultServiceImpl;
import cz.sportiq.service.mapper.*;
import cz.sportiq.service.utils.StatsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        StatsDTO stats = getStatsForActivityResult(activityResult, athleteActivity, athleteId);
        athleteActivityResultSummary.setStats(stats);

        return athleteActivityResultSummary;
    }

    StatsDTO getStatsForActivityResult(ActivityResult activityResult, AthleteActivity athleteActivity, Long athleteId) {
        Event event = athleteActivity.getAthleteWorkout().getAthleteEvent().getEvent();
        List<AthleteActivityResult> activityResultsForEvent = athleteActivityResultRepository.findActivityResultsByEventId(activityResult.getId(), event.getId());
        Optional<AthleteActivityResult> athleteActivityResultOptional = activityResultsForEvent.stream().filter(ars -> ars.getAthleteActivity().getId().longValue() == athleteActivity.getId()).findFirst();

        if(!athleteActivityResultOptional.isPresent()) {
            return null;
        }

        StatsDTO stats = new StatsDTO();
        stats.setTotalCount(activityResultsForEvent.size());
        boolean hasValues = false;
        boolean hasCompareValues = false;

        List<AthleteActivityResult> allResultsWithValue = activityResultsForEvent.stream().filter(r -> r.getValue() != null).collect(Collectors.toList());

        if(allResultsWithValue != null && !allResultsWithValue.isEmpty()) {
            ResultStatsDTO resultValueStats  = new ResultStatsDTO();
            hasValues = true;
            Collections.sort(allResultsWithValue, new AthleteActivityResultValueComparator());
            List<Float> allResultValues = allResultsWithValue.stream().map(r -> r.getValue()).collect(Collectors.toList());

            resultValueStats.setBestResult(athleteActivityResultMapper.toDto(allResultsWithValue.get(0)));
            resultValueStats.setBest(resultValueStats.getBestResult().getValue());
            resultValueStats.setWorstResult(athleteActivityResultMapper.toDto(allResultsWithValue.get(allResultsWithValue.size() - 1)));
            resultValueStats.setWorst(resultValueStats.getWorstResult().getValue());
            resultValueStats.setAverage(StatsUtil.average(allResultValues));
            stats.setResultValueStats(resultValueStats);
        }


        List<AthleteActivityResult> allResultsWithCompareValue = activityResultsForEvent.stream().filter(r -> r.getCompareValue() != null).collect(Collectors.toList());
        if(allResultsWithCompareValue != null && !allResultsWithCompareValue.isEmpty()) {
            ResultStatsDTO resultCompareValueStats  = new ResultStatsDTO();
            hasCompareValues = true;
            Collections.sort(allResultsWithCompareValue, new AthleteActivityResultCompareValueComparator());
            List<Float> allResultCompareValues = allResultsWithCompareValue.stream().map(r -> r.getCompareValue()).collect(Collectors.toList());

            resultCompareValueStats.setBestResult(athleteActivityResultMapper.toDto(allResultsWithCompareValue.get(0)));
            resultCompareValueStats.setBest(resultCompareValueStats.getBestResult().getCompareValue());
            resultCompareValueStats.setWorstResult(athleteActivityResultMapper.toDto(allResultsWithCompareValue.get(allResultsWithCompareValue.size() - 1)));
            resultCompareValueStats.setWorst(resultCompareValueStats.getWorstResult().getCompareValue());
            resultCompareValueStats.setAverage(StatsUtil.average(allResultCompareValues));
            stats.setResultCompareValueStats(resultCompareValueStats);
        }


        List<AthleteActivityResult> allResultWithResultSplitsWithValue = activityResultsForEvent
            .stream()
            .filter(r -> StatsUtil.athleteActivityResultHasSplitsWithValue(r))
            .collect(Collectors.toList());
        if(allResultWithResultSplitsWithValue != null && !allResultWithResultSplitsWithValue.isEmpty()) {
            ResultStatsDTO resultSplitsValueStats  = new ResultStatsDTO();
            Collections.sort(allResultWithResultSplitsWithValue, new AthleteActivityResultSplitsValueComparator());
            List<Float> allResultSplitsValuesSums = allResultsWithValue.stream().map(r -> StatsUtil.getSumOfSplitValues(r)).collect(Collectors.toList());

            resultSplitsValueStats.setBestResult(athleteActivityResultMapper.toDto(allResultWithResultSplitsWithValue.get(0)));
            resultSplitsValueStats.setBest(StatsUtil.getSumOfSplitValues(allResultWithResultSplitsWithValue.get(0)));
            resultSplitsValueStats.setWorstResult(athleteActivityResultMapper.toDto(allResultWithResultSplitsWithValue.get(allResultWithResultSplitsWithValue.size() - 1)));
            resultSplitsValueStats.setWorst(StatsUtil.getSumOfSplitValues(allResultWithResultSplitsWithValue.get(allResultWithResultSplitsWithValue.size() - 1)));
            resultSplitsValueStats.setAverage(StatsUtil.average(allResultSplitsValuesSums));
            stats.setResultSplitsValueStats(resultSplitsValueStats);
        }

        List<AthleteActivityResult> allResultWithResultSplitsWithCompareValue = activityResultsForEvent
            .stream()
            .filter(r -> StatsUtil.athleteActivityResultHasSplitsWithCompareValue(r))
            .collect(Collectors.toList());
        if(allResultWithResultSplitsWithCompareValue != null && !allResultWithResultSplitsWithCompareValue.isEmpty()) {
            ResultStatsDTO resultSplitsCompareValueStats  = new ResultStatsDTO();
            Collections.sort(allResultWithResultSplitsWithCompareValue, new AthleteActivityResultSplitsCompareValueComparator());
            List<Float> allResultSplitsCompareValuesSum = allResultWithResultSplitsWithCompareValue.stream().map(r -> StatsUtil.getSumOfSplitCompareValues(r)).collect(Collectors.toList());

            resultSplitsCompareValueStats.setBestResult(athleteActivityResultMapper.toDto(allResultWithResultSplitsWithCompareValue.get(0)));
            resultSplitsCompareValueStats.setBest(StatsUtil.getSumOfSplitCompareValues(allResultWithResultSplitsWithCompareValue.get(0)));
            resultSplitsCompareValueStats.setWorstResult(athleteActivityResultMapper.toDto(allResultWithResultSplitsWithCompareValue.get(allResultWithResultSplitsWithCompareValue.size() - 1)));
            resultSplitsCompareValueStats.setWorst(StatsUtil.getSumOfSplitCompareValues(allResultWithResultSplitsWithCompareValue.get(allResultWithResultSplitsWithCompareValue.size() - 1)));
            resultSplitsCompareValueStats.setAverage(StatsUtil.average(allResultSplitsCompareValuesSum));
            stats.setResultSplitsCompareValueStats(resultSplitsCompareValueStats);
        }

        AthleteStatsDTO athleteStats = new AthleteStatsDTO();
        if (hasValues) {
            int rank = allResultsWithValue.indexOf(athleteActivityResultOptional.get());
            athleteStats.setValueRank(rank);
            athleteStats.setValueRankInPercents(StatsUtil.getRankInPercents(athleteStats.getValueRank(), stats.getTotalCount()));
        }

        if (hasCompareValues) {
            int rank = allResultsWithCompareValue.indexOf(athleteActivityResultOptional.get());
            athleteStats.setCompareValueRank(rank);
            athleteStats.setCompareValueRankInPercents(StatsUtil.getRankInPercents(athleteStats.getCompareValueRank(), stats.getTotalCount()));
        }
        stats.setAthleteStats(athleteStats);


        return stats;
    }
}

