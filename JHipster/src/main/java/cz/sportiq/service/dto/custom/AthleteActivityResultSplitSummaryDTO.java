package cz.sportiq.service.dto.custom;

import cz.sportiq.service.dto.ActivityResultSplitDTO;
import cz.sportiq.service.dto.AthleteActivityResultSplitDTO;

public class AthleteActivityResultSplitSummaryDTO {

    private AthleteActivityResultSplitDTO athleteActivityResultSplit;
    private ActivityResultSplitDTO activityResultSplit;

    private StatsDTO stats;


    public AthleteActivityResultSplitDTO getAthleteActivityResultSplit() {
        return athleteActivityResultSplit;
    }

    public void setAthleteActivityResultSplit(AthleteActivityResultSplitDTO athleteActivityResultSplit) {
        this.athleteActivityResultSplit = athleteActivityResultSplit;
    }

    public ActivityResultSplitDTO getActivityResultSplit() {
        return activityResultSplit;
    }

    public void setActivityResultSplit(ActivityResultSplitDTO activityResultSplit) {
        this.activityResultSplit = activityResultSplit;
    }


    public StatsDTO getStats() {
        return stats;
    }

    public void setStats(StatsDTO stats) {
        this.stats = stats;
    }
}
