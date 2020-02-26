package cz.sportiq.service.dto.custom;

public class StatsDTO {
    private Integer totalCount;

    private Float bestValue;
    private Float worstValue;
    private Float averageValue;

    private Float bestCompareValue;
    private Float worstCompareValue;
    private Float averageCompareValue;

    private AthleteStatsDTO athleteStats;

    public Float getBestValue() {
        return bestValue;
    }

    public void setBestValue(Float bestValue) {
        this.bestValue = bestValue;
    }

    public Float getWorstValue() {
        return worstValue;
    }

    public void setWorstValue(Float worstValue) {
        this.worstValue = worstValue;
    }

    public Float getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(Float averageValue) {
        this.averageValue = averageValue;
    }

    public Float getBestCompareValue() {
        return bestCompareValue;
    }

    public void setBestCompareValue(Float bestCompareValue) {
        this.bestCompareValue = bestCompareValue;
    }

    public Float getWorstCompareValue() {
        return worstCompareValue;
    }

    public void setWorstCompareValue(Float worstCompareValue) {
        this.worstCompareValue = worstCompareValue;
    }

    public Float getAverageCompareValue() {
        return averageCompareValue;
    }

    public void setAverageCompareValue(Float averageCompareValue) {
        this.averageCompareValue = averageCompareValue;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public AthleteStatsDTO getAthleteStats() {
        return athleteStats;
    }

    public void setAthleteStats(AthleteStatsDTO athleteStats) {
        this.athleteStats = athleteStats;
    }
}
