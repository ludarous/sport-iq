import {AthleteStats} from './athlete-stats';

export interface Stats {
    totalCount?: number;

    bestValue?: number;
    worstValue?: number;
    averageValue?: number;

    bestCompareValue?: number;
    worstCompareValue?: number;
    averageCompareValue?: number;

    athleteStats?: AthleteStats;
}
