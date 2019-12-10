package cz.sportiq.service.dto.custom;

import cz.sportiq.service.dto.ActivityResultDTO;
import cz.sportiq.service.dto.AthleteActivityResultDTO;

import java.util.Set;

public class AthleteActivityResultSummaryDTO {

    private AthleteActivityResultDTO athleteActivityResult;
    private ActivityResultDTO activityResult;

    private Set<AthleteActivityResultSplitSummaryDTO> resultSplitSummaries;

    private StatsDTO stats;

    public AthleteActivityResultDTO getAthleteActivityResult() {
        return athleteActivityResult;
    }

    public void setAthleteActivityResult(AthleteActivityResultDTO athleteActivityResult) {
        this.athleteActivityResult = athleteActivityResult;
    }

    public ActivityResultDTO getActivityResult() {
        return activityResult;
    }

    public void setActivityResult(ActivityResultDTO activityResult) {
        this.activityResult = activityResult;
    }

    public Set<AthleteActivityResultSplitSummaryDTO> getResultSplitSummaries() {
        return resultSplitSummaries;
    }

    public void setResultSplitSummaries(Set<AthleteActivityResultSplitSummaryDTO> resultSplitSummaries) {
        this.resultSplitSummaries = resultSplitSummaries;
    }

    public StatsDTO getStats() {
        return stats;
    }

    public void setStats(StatsDTO stats) {
        this.stats = stats;
    }
}
