package cz.sportiq.service.dto.custom;

public class AthleteStatsDTO {
    private Integer valueRank;
    private Float valueRankInPercents;

    private Integer compareValueRank;
    private Float compareValueRankInPercents;

    public Integer getValueRank() {
        return valueRank;
    }

    public void setValueRank(Integer valueRank) {
        this.valueRank = valueRank;
    }

    public Float getValueRankInPercents() {
        return valueRankInPercents;
    }

    public void setValueRankInPercents(Float valueRankInPercents) {
        this.valueRankInPercents = valueRankInPercents;
    }

    public Integer getCompareValueRank() {
        return compareValueRank;
    }

    public void setCompareValueRank(Integer compareValueRank) {
        this.compareValueRank = compareValueRank;
    }

    public Float getCompareValueRankInPercents() {
        return compareValueRankInPercents;
    }

    public void setCompareValueRankInPercents(Float compareValueRankInPercents) {
        this.compareValueRankInPercents = compareValueRankInPercents;
    }
}
