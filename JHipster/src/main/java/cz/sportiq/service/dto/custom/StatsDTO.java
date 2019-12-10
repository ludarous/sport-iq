package cz.sportiq.service.dto.custom;

public class StatsDTO {
    private Integer totalCount;

    private Float bestValue;
    private Float worstValue;
    private Float averageValue;
    private Float rank;

    private Float bestCompareValue;
    private Float worstCompareValue;
    private Float averageCompareValue;
    private Float compareValueRank;

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

    public Float getRank() {
        return rank;
    }

    public void setRank(Float rank) {
        this.rank = rank;
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

    public Float getCompareValueRank() {
        return compareValueRank;
    }

    public void setCompareValueRank(Float compareValueRank) {
        this.compareValueRank = compareValueRank;
    }
}
