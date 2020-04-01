import { AthleteStats } from './athlete-stats';
import { AthleteActivityResultSummary } from './athlete-activity-result-summary';
import { IAthleteActivityResult } from '../model/athlete-activity-result.model';

export interface ResultStats {
    best?: number;
    bestResult?: IAthleteActivityResult;

    worst?: number;
    worstResult?: IAthleteActivityResult;

    average?: number;
}

export interface Stats {
    totalCount?: number;

    resultValueStats?: ResultStats;
    resultCompareValueStats?: ResultStats;
    resultSplitsValueStats?: ResultStats;
    resultSplitsCompareValueStats?: ResultStats;

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
                stats.resultValueStats.best,
                stats.resultValueStats.worst,
                stats.resultValueStats.average,
                stats.athleteStats.valueRank,
                stats.athleteStats.valueRankInPercents);

            statsTableItems.push(valueStats);
        }

        if (compareValue) {
            const compareValueStats = new StatsTableItem(
                'S míčem',
                compareValue,
                stats.resultCompareValueStats.best,
                stats.resultCompareValueStats.worst,
                stats.resultCompareValueStats.average,
                stats.athleteStats.compareValueRank,
                stats.athleteStats.compareValueRankInPercents);

            statsTableItems.push(compareValueStats);
        }

        if (value && compareValue) {
            const diffValuesStats = new StatsTableItem('Rozdíl');
            diffValuesStats.value = value - compareValue;
            diffValuesStats.averageValue = stats.resultValueStats.average - stats.resultCompareValueStats.average;
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
        let statsTableItems = null;
        const athleteResult = athleteActivityResultSummary.athleteActivityResult;

        if (athleteActivityResultSummary.stats &&
            athleteResult.athleteActivityResultSplits &&
            athleteResult.athleteActivityResultSplits.length > 0) {

            statsTableItems = new Array();

            for (const resultSplit of athleteActivityResultSummary.activityResult.resultSplits) {

                let valueSplitTableItem: StatsTableItem = null;
                let compareValueSplitTableItem: StatsTableItem = null;
                let diffValueSplitTableItem = null;


                const athleteSplit = athleteResult.athleteActivityResultSplits
                        .find(as => as.activityResultSplitId === resultSplit.id);
                const setLabel = athleteSplit.value && athleteSplit.compareValue;

                if (athleteActivityResultSummary.stats.resultSplitsValueStats && athleteSplit.value) {
                    const bestValueResult = athleteActivityResultSummary.stats.resultSplitsValueStats.bestResult;
                    const worstValueResult = athleteActivityResultSummary.stats.resultSplitsValueStats.worstResult;

                    const bestValueSplit = bestValueResult.athleteActivityResultSplits
                        .find(as => as.activityResultSplitId === resultSplit.id);

                    const worstValueSplit = worstValueResult.athleteActivityResultSplits
                        .find(as => as.activityResultSplitId === resultSplit.id);

                    valueSplitTableItem = new StatsTableItem(
                        setLabel ? 'Bez míče' : null,
                        athleteSplit.value,
                        bestValueSplit.value,
                        worstValueSplit.value,
                        null,
                        null,
                        null,
                        resultSplit.splitValue + '. ' + resultSplit.splitUnit.name);
                    statsTableItems.push(valueSplitTableItem);
                }

                if (athleteResult.athleteActivityResultSplits && athleteSplit.compareValue) {

                    const bestCompareValueResult = athleteActivityResultSummary.stats.resultSplitsCompareValueStats.bestResult;
                    const worstCompareValueResult = athleteActivityResultSummary.stats.resultSplitsCompareValueStats.worstResult;

                    const bestCompareValueSplit = bestCompareValueResult.athleteActivityResultSplits
                        .find(as => as.activityResultSplitId === resultSplit.id);

                    const worstCompareValueSplit = worstCompareValueResult.athleteActivityResultSplits
                        .find(as => as.activityResultSplitId === resultSplit.id);

                    compareValueSplitTableItem = new StatsTableItem(
                        setLabel ? 'S míčem' : null,
                        athleteSplit.compareValue,
                        bestCompareValueSplit.compareValue,
                        worstCompareValueSplit.compareValue,
                        null,
                        null,
                        null,
                        resultSplit.splitValue + '. ' + resultSplit.splitUnit.name);
                    statsTableItems.push(compareValueSplitTableItem);
                }


                if (athleteSplit.compareValue && athleteSplit.value) {
                    diffValueSplitTableItem = new StatsTableItem(
                        setLabel ? 'Rozdíl' : null,
                        athleteSplit.value - athleteSplit.compareValue,
                        valueSplitTableItem.bestValue - compareValueSplitTableItem.bestValue,
                        valueSplitTableItem.worstValue - compareValueSplitTableItem.worstValue);
                    statsTableItems.push(diffValueSplitTableItem);
                }
            }
        }
        return statsTableItems;
    }
}
