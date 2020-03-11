package cz.sportiq.service.dto.custom;

import cz.sportiq.service.dto.AthleteActivityResultDTO;

public class ResultStatsDTO {

    private Float best;
    private AthleteActivityResultDTO bestResult;

    private Float worst;
    private AthleteActivityResultDTO worstResult;

    private Float average;

    public Float getBest() {
        return best;
    }

    public void setBest(Float best) {
        this.best = best;
    }

    public AthleteActivityResultDTO getBestResult() {
        return bestResult;
    }

    public void setBestResult(AthleteActivityResultDTO bestResult) {
        this.bestResult = bestResult;
    }

    public Float getWorst() {
        return worst;
    }

    public void setWorst(Float worst) {
        this.worst = worst;
    }

    public AthleteActivityResultDTO getWorstResult() {
        return worstResult;
    }

    public void setWorstResult(AthleteActivityResultDTO worstResult) {
        this.worstResult = worstResult;
    }

    public Float getAverage() {
        return average;
    }

    public void setAverage(Float average) {
        this.average = average;
    }
}
