package cz.sportiq.service.dto.custom;

import cz.sportiq.service.dto.AthleteActivityResultDTO;

public class StatsDTO {
    private Integer totalCount;

    private ResultStatsDTO resultValueStats;
    private ResultStatsDTO resultCompareValueStats;
    private ResultStatsDTO resultSplitsValueStats;
    private ResultStatsDTO resultSplitsCompareValueStats;

    private AthleteStatsDTO athleteStats;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }


    public ResultStatsDTO getResultValueStats() {
        return resultValueStats;
    }

    public void setResultValueStats(ResultStatsDTO resultValueStats) {
        this.resultValueStats = resultValueStats;
    }

    public ResultStatsDTO getResultCompareValueStats() {
        return resultCompareValueStats;
    }

    public void setResultCompareValueStats(ResultStatsDTO resultCompareValueStats) {
        this.resultCompareValueStats = resultCompareValueStats;
    }

    public ResultStatsDTO getResultSplitsValueStats() {
        return resultSplitsValueStats;
    }

    public void setResultSplitsValueStats(ResultStatsDTO resultSplitsValueStats) {
        this.resultSplitsValueStats = resultSplitsValueStats;
    }

    public ResultStatsDTO getResultSplitsCompareValueStats() {
        return resultSplitsCompareValueStats;
    }

    public void setResultSplitsCompareValueStats(ResultStatsDTO resultSplitsCompareValueStats) {
        this.resultSplitsCompareValueStats = resultSplitsCompareValueStats;
    }

    public AthleteStatsDTO getAthleteStats() {
        return athleteStats;
    }

    public void setAthleteStats(AthleteStatsDTO athleteStats) {
        this.athleteStats = athleteStats;
    }
}
