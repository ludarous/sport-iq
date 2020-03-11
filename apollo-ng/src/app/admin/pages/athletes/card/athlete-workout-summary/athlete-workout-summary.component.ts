import { Component, Input, OnInit } from '@angular/core';
import { AthleteWorkoutSummary } from '../../../../entities/summaries/athlete-workout-summary';
import { ArrayUtils } from '../../../../../modules/core/utils/array.utils';
import { Activity } from '../../../../entities/model/activity.model';
import { colorSets } from '@swimlane/ngx-charts/release/utils';
import { AthleteActivitySummary } from '../../../../entities/summaries/athlete-activity-summary';
import { AthleteActivityResult } from '../../../../entities/model/athlete-activity-result.model';
import { ActivityResult } from '../../../../entities/model/activity-result.model';
import { ResultStats, Stats } from '../../../../entities/summaries/stats';
import { Athlete } from '../../../../entities/model/athlete.model';
import { MathUtils } from '../../../../../modules/core/utils/math-utils';
import { AthleteActivityResultSummary } from '../../../../entities/summaries/athlete-activity-result-summary';

export interface ActivityChartData {
    activityName: string;
    resultChartData: ResultChartData;
}

export interface ResultChartData {
    yAxisLabel: string;
    xAxisLabel: string;
    valueData?: Array<any>;
    valueCardData?: any;

    compareValueData?: Array<any>;
    compareValueCardData?: any;

    splitsValueData?: Array<any>;
    splitsValueCardData?: any;

    splitsCompareValueData?: Array<any>;
    splitsCompareValueCardData?: any;
}


@Component({
    selector: 'app-athlete-workout-summary',
    templateUrl: './athlete-workout-summary.component.html',
    styleUrls: ['./athlete-workout-summary.component.scss']
})
export class AthleteWorkoutSummaryComponent implements OnInit {

    // options
    showXAxis = true;
    showYAxis = true;
    gradient = true;
    showXAxisLabel = true;
    showYAxisLabel = true;
    colorScheme = colorSets.find(s => s.name === 'cool');
    schemeType = 'linear';

    activitySummariesCharts: Array<ActivityChartData>;

    constructor() {

    }


    private _workoutSummary: AthleteWorkoutSummary;
    @Input()
    get workoutSummary(): AthleteWorkoutSummary {
        return this._workoutSummary;
    }

    set workoutSummary(value: AthleteWorkoutSummary) {
        if (value) {
            if (value.activitySummaries) {
                value.activitySummaries = Activity.sortActivitySummariesByActivityId(value.activitySummaries, 1);
                this.activitySummariesCharts = new Array<ActivityChartData>();
                for (const activitySummary of value.activitySummaries) {
                    this.activitySummariesCharts.push({
                        activityName: activitySummary.activity.name,
                        resultChartData: this.getActivityResultCharts(activitySummary),
                    });
                }
            }
            this._workoutSummary = value;
        }
    }

    ngOnInit() {

    }

    getActivityResultCharts(activitySummary: AthleteActivitySummary): ResultChartData {
        const resultSummaries = AthleteActivityResultSummary.sortByActivityResultId(activitySummary.resultSummaries);
        const resultSummary = resultSummaries[0];
        const resultChart: ResultChartData = {
            yAxisLabel: activitySummary.activity.name,
            xAxisLabel: resultSummary.activityResult.resultUnit.name,
        };

        if (resultSummary.athleteActivityResult.value && resultSummary.stats.resultValueStats) {

            resultChart.valueData = this.getValueData(resultSummary.athleteActivityResult,
                resultSummary.stats.resultValueStats,
                'value')

            resultChart.valueCardData = [{
                name: resultSummary.activityResult.name,
                value: resultSummary.athleteActivityResult.value,
                extra: {
                    unit: resultSummary.activityResult.resultUnit.abbreviation
                }
            }];
        }

        if (resultSummary.athleteActivityResult.compareValue  && resultSummary.stats.resultCompareValueStats) {
            resultChart.compareValueData = this.getValueData(resultSummary.athleteActivityResult,
                resultSummary.stats.resultCompareValueStats,
                'compareValue')

            resultChart.compareValueCardData = [{
                name: resultSummary.activityResult.name + ' s míčem',
                value: resultSummary.athleteActivityResult.compareValue,
                extra: {
                    unit: resultSummary.activityResult.resultUnit.abbreviation
                }
            }];
        }


        resultChart.splitsValueData = this.getSplitsResultData(
            resultSummary.athleteActivityResult,
            resultSummary.activityResult,
            resultSummary.stats.resultSplitsValueStats,
            'value');


        const cardValue = MathUtils.sum(resultSummary.athleteActivityResult.athleteActivityResultSplits.map(rs => rs.value));
        if (cardValue) {
            resultChart.splitsValueCardData = [{
                name: resultSummary.activityResult.name + ' (kumulativní)',
                value: cardValue.toFixed(3),
                extra: {
                    unit: resultSummary.activityResult.resultUnit.abbreviation
                }
            }];
        }

        resultChart.splitsCompareValueData = this.getSplitsResultData(
            resultSummary.athleteActivityResult,
            resultSummary.activityResult,
            resultSummary.stats.resultSplitsCompareValueStats,
            'compareValue');

        const cardCompareValue = MathUtils.sum(resultSummary.athleteActivityResult.athleteActivityResultSplits.map(rs => rs.compareValue));
        if (cardCompareValue) {
            resultChart.splitsCompareValueCardData = [{
                name: resultSummary.activityResult.name + ' (kumulativní)',
                value: cardCompareValue.toFixed(3),
                extra: {
                    unit: resultSummary.activityResult.resultUnit.abbreviation
                }
            }];
        }


        return resultChart;
    }

    valueFormat(c): string {
        if (c.data && c.data.extra) {
            return c.value + ' ' + c.data.extra.unit;
        }
        return c.value;
    }

    getValueData(athleteActivityResult: AthleteActivityResult, resultStats: ResultStats, valueProperty: 'value' | 'compareValue'): Array<any> {
        const valueData = [
            {
                name: 'Výsledek',
                value: athleteActivityResult[valueProperty]
            },
            {
                name: 'Nejlepší',
                value: resultStats.best
            },
            {
                name: 'Nejhorší',
                value: resultStats.worst
            }];
        return valueData;
    }

    getSplitsValueData(athleteActivityResult: AthleteActivityResult,
                       activityResult: ActivityResult,
                       valueProperty: 'value' | 'compareValue',
                       resultName: string): any {
        const athleteSplits = athleteActivityResult.athleteActivityResultSplits;
        if (!athleteSplits || athleteSplits.length <= 0) {
            return null;
        }

        const resultSplitsData = {
            name: resultName,
            series: []
        };

        for (const athleteSplit of athleteActivityResult.athleteActivityResultSplits) {
            const splitResult = activityResult.resultSplits
                .find((s) => s.id === athleteSplit.activityResultSplitId);
            const serie = {
                name: splitResult.splitValue + '. ' + splitResult.splitUnit.name,
                value: athleteSplit[valueProperty]
            };

            resultSplitsData.series.push(serie);
        }

        return resultSplitsData;
    }

    getSplitsResultData(athleteActivityResult: AthleteActivityResult,
                        activityResult: ActivityResult,
                        stats: ResultStats,
                        valueProperty: 'value' | 'compareValue'): Array<any> {


        if (!stats) {
            return null;
        }

        const splitsData = [];
        const valueData = this.getSplitsValueData(athleteActivityResult, activityResult, valueProperty, 'Výsledek');
        if (valueData) {
            splitsData.push(valueData);
        }

        if (stats.bestResult) {
            const bestValueData = this.getSplitsValueData(stats.bestResult, activityResult, valueProperty, 'Nejlepší');
            if (bestValueData) {
                splitsData.push(bestValueData);
            }
        }

        if (stats.worstResult) {
            const worstValueData = this.getSplitsValueData(stats.worstResult, activityResult, valueProperty, 'Nejhorší');
            if (worstValueData) {
                splitsData.push(worstValueData);
            }
        }
        return splitsData;


    }

}
