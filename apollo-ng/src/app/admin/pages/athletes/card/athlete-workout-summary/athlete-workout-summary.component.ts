import { Component, Input, OnInit } from '@angular/core';
import { AthleteWorkoutSummary } from '../../../../entities/summaries/athlete-workout-summary';
import { ArrayUtils } from '../../../../../modules/core/utils/array.utils';
import { Activity } from '../../../../entities/model/activity.model';
import { colorSets } from '@swimlane/ngx-charts/release/utils';
import { AthleteActivitySummary } from '../../../../entities/summaries/athlete-activity-summary';

export interface ActivityChartData {
    activityName: string;
    resultChartData: ResultChartData;
}

export interface ResultChartData {
    yAxisLabel: string;
    xAxisLabel: string;
    primaryData?: Array<any>;
    compareData?: Array<any>;
    primaryCardData?: any;
    compareCardData?: any;
    primarySplitsData?: Array<any>;
    compareSplitsData?: Array<any>;
}


@Component({
    selector: 'app-athlete-workout-summary',
    templateUrl: './athlete-workout-summary.component.html',
    styleUrls: ['./athlete-workout-summary.component.scss']
})
export class AthleteWorkoutSummaryComponent implements OnInit {

    // options
    showXAxis: boolean = true;
    showYAxis: boolean = true;
    gradient: boolean = true;
    showXAxisLabel: boolean = true;
    showYAxisLabel: boolean = true;
    colorScheme = colorSets.find(s => s.name === 'cool');
    schemeType: string = 'linear';

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
        const resultSummary = activitySummary.resultSummaries[0];
        const resultChart: ResultChartData = {
            yAxisLabel: activitySummary.activity.name,
            xAxisLabel: resultSummary.activityResult.resultUnit.name,
        };

        if (resultSummary.athleteActivityResult.value) {

            resultChart.primaryData = [
                {
                    name: 'Výsledek',
                    value: resultSummary.athleteActivityResult.value
                },
                {
                    name: 'Nejlepší',
                    value: resultSummary.stats.bestValue
                },
                {
                    name: 'Nejhorší',
                    value: resultSummary.stats.worstValue
                }];

            resultChart.primaryCardData = [{
                name: resultSummary.activityResult.name,
                value: resultSummary.athleteActivityResult.value,
                extra: {
                    unit: resultSummary.activityResult.resultUnit.abbreviation
                }
            }];
        }

        if (resultSummary.athleteActivityResult.compareValue) {
            resultChart.compareData = [
                {
                    name: 'Výsledek',
                    value: resultSummary.athleteActivityResult.compareValue
                },
                {
                    name: 'Nejlepší',
                    value: resultSummary.stats.bestCompareValue
                },
                {
                    name: 'Nejhorší',
                    value: resultSummary.stats.worstCompareValue
                }];

            resultChart.compareCardData = [{
                name: resultSummary.activityResult.name,
                value: resultSummary.athleteActivityResult.compareValue,
                extra: {
                    unit: resultSummary.activityResult.resultUnit.abbreviation
                }
            }];
        }

        const athleteSplits = resultSummary.athleteActivityResult.athleteActivityResultSplits;
        if (athleteSplits && athleteSplits.length > 0) {
           const splitsData = {
               name: resultSummary.activityResult.name,
               series: []
           };

            const compareSplitsData = {
                name: resultSummary.activityResult.name,
                series: []
            };

           for (const athleteSplit of athleteSplits) {
                const splitResult = resultSummary.activityResult.resultSplits.find((s) => s.id === athleteSplit.activityResultSplitId);
                const primarySerie = {
                    name: splitResult.splitValue + '. ' + splitResult.splitUnit.name,
                    value: athleteSplit.value
                };

               const compareSerie = {
                   name: splitResult.splitValue + '. ' + splitResult.splitUnit.name,
                   value: athleteSplit.compareValue
               };
                splitsData.series.push(primarySerie);
                compareSplitsData.series.push(compareSerie);
           }

           resultChart.primarySplitsData = [splitsData];
           resultChart.compareSplitsData = [compareSplitsData];


        }





        return resultChart;
    }

    valueFormat(c): string {
        if (c.data && c.data.extra) {
            return c.value + ' ' + c.data.extra.unit;
        }
        return c.value;
    }

}
