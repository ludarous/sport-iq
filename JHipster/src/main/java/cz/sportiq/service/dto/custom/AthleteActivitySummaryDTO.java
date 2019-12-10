package cz.sportiq.service.dto.custom;

import cz.sportiq.service.dto.ActivityDTO;
import cz.sportiq.service.dto.AthleteActivityDTO;

import java.util.Set;

public class AthleteActivitySummaryDTO {
    private AthleteActivityDTO athleteActivity;
    private ActivityDTO activity;

    private Set<AthleteActivityResultSummaryDTO> resultSummaries;

    public AthleteActivityDTO getAthleteActivity() {
        return athleteActivity;
    }

    public void setAthleteActivity(AthleteActivityDTO athleteActivity) {
        this.athleteActivity = athleteActivity;
    }

    public ActivityDTO getActivity() {
        return activity;
    }

    public void setActivity(ActivityDTO activity) {
        this.activity = activity;
    }

    public Set<AthleteActivityResultSummaryDTO> getResultSummaries() {
        return resultSummaries;
    }

    public void setResultSummaries(Set<AthleteActivityResultSummaryDTO> resultSummaries) {
        this.resultSummaries = resultSummaries;
    }
}
