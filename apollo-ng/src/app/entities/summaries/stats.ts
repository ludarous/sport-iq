import {AthleteStats} from './athlete-stats';
import {AthleteActivityResultSummary} from './athlete-activity-result-summary';

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
    splitLabel: string;

    constructor(label: string = null,
                value: number = null,
                bestValue: number = null,
                worstValue: number = null,
                averageValue: number = null,
                rank: number = null,
                rankInPercents: number = null,
                splitLabel: string = null) {
        this.label = label;
        this.value = value;
        this.bestValue = bestValue;
        this.worstValue = worstValue;
        this.averageValue = averageValue;
        this.rank = rank;
        this.rankInPercents = rankInPercents;
        this.splitLabel = splitLabel;
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



    static createResultsStatsTableItems(athleteActivityResultSummary: AthleteActivityResultSummary): Array<StatsTableItem> {
        return StatsTableItem.createStatsTableItems(
            athleteActivityResultSummary.athleteActivityResult.value,
            athleteActivityResultSummary.athleteActivityResult.compareValue,
            athleteActivityResultSummary.stats);
    }

    static createSplitStatsTableItems(athleteActivityResultSummary: AthleteActivityResultSummary): Array<StatsTableItem> {
        if (athleteActivityResultSummary.resultSplitSummaries && athleteActivityResultSummary.resultSplitSummaries.length > 0) {
            const statsTableItems = new Array();
            let i = 1;
            for (const resultSplitSummary of athleteActivityResultSummary.resultSplitSummaries) {

                let valueSplitTableItem = null;
                let compareValueSplitTableItem = null;
                let diffValueSplitTableItem = null;

                const setLabel = resultSplitSummary.athleteActivityResultSplit.value &&
                    resultSplitSummary.athleteActivityResultSplit.compareValue;

                if (resultSplitSummary.athleteActivityResultSplit.value) {
                    valueSplitTableItem = new StatsTableItem(
                        setLabel ? 'Bez míče' : null,
                        resultSplitSummary.athleteActivityResultSplit.value,
                        resultSplitSummary.stats.bestValue,
                        resultSplitSummary.stats.worstValue,
                        resultSplitSummary.stats.averageValue,
                        resultSplitSummary.stats.athleteStats.valueRank,
                        resultSplitSummary.stats.athleteStats.valueRankInPercents,
                        i + '. ' + resultSplitSummary.activityResultSplit.splitUnit.name);
                    statsTableItems.push(valueSplitTableItem);
                }

                if (resultSplitSummary.athleteActivityResultSplit.compareValue) {
                    compareValueSplitTableItem = new StatsTableItem(
                        setLabel ? 'S míčem' : null,
                        resultSplitSummary.athleteActivityResultSplit.compareValue,
                        resultSplitSummary.stats.bestCompareValue,
                        resultSplitSummary.stats.worstCompareValue,
                        resultSplitSummary.stats.averageCompareValue,
                        resultSplitSummary.stats.athleteStats.compareValueRank,
                        resultSplitSummary.stats.athleteStats.compareValueRankInPercents,
                        resultSplitSummary.athleteActivityResultSplit.value ? null : (i + '. ' + resultSplitSummary.activityResultSplit.splitUnit.name));

                    statsTableItems.push(compareValueSplitTableItem);
                }

                if (resultSplitSummary.athleteActivityResultSplit.compareValue && resultSplitSummary.athleteActivityResultSplit.value) {
                    diffValueSplitTableItem = new StatsTableItem(
                        setLabel ? 'Rozdíl' : null,
                        resultSplitSummary.athleteActivityResultSplit.value - resultSplitSummary.athleteActivityResultSplit.compareValue,
                        resultSplitSummary.stats.averageValue - resultSplitSummary.stats.averageCompareValue,);
                    statsTableItems.push(diffValueSplitTableItem);
                }

                i++;
                statsTableItems.push(null);
            }
            return statsTableItems;
        }
        return null;
    }
}
