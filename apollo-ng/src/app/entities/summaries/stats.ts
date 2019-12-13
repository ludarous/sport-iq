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

export class StatsTableItem {
    label: string;
    value: number;
    bestValue: number;
    worstValue: number;
    averageValue: number;
    rank: number;
    rankInPercents: number;

    constructor(label: string = null, value: number = null, bestValue: number = null, worstValue: number = null, averageValue: number = null, rank: number = null, rankInPercents: number = null) {
        this.label = label;
        this.value = value;
        this.bestValue = bestValue;
        this.worstValue = worstValue;
        this.averageValue = averageValue;
        this.rank = rank;
        this.rankInPercents = rankInPercents;
    }

    static createStatsTableItems(value: number, compareValue: number, stats: Stats): Array<StatsTableItem> {
        const statsTableItems = new Array();

        if (value) {
            const valueStats = new StatsTableItem('Bez míče',
                value,
                stats.bestValue,
                stats.worstValue,
                stats.averageValue,
                stats.athleteStats.valueRank,
                stats.athleteStats.valueRankInPercents);

            statsTableItems.push(valueStats);
        }

        if (compareValue) {
            const compareValueStats = new StatsTableItem(
                'S míčem',
                compareValue,
                stats.bestCompareValue,
                stats.worstCompareValue,
                stats.averageCompareValue,
                stats.athleteStats.compareValueRank,
                stats.athleteStats.compareValueRankInPercents);

            statsTableItems.push(compareValueStats);
        }

        if (value && compareValue) {
            const diffValuesStats = new StatsTableItem('Rozdíl');
            diffValuesStats.value = value - compareValue;
            diffValuesStats.averageValue = stats.averageValue - stats.averageCompareValue;
            statsTableItems.push(diffValuesStats);
        }

        return statsTableItems;

    }
}
