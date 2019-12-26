import {Component, Input, OnInit} from '@angular/core';
import {AthleteActivityResultSummary} from '../../../../entities/summaries/athlete-activity-result-summary';
import {StatsTableItem} from '../../../../entities/summaries/stats';
import * as shape from 'd3-shape';
import * as d3Array from 'd3-array';
import {ResultType} from '../../../../entities/model/activity-result.model';
import {ActivityResultSplit} from '../../../../entities/model/activity-result-split.model';
import {colorSets} from '@swimlane/ngx-charts/release/utils';

export class SplitResultChartData {
    name: string;
    series: any[];

    constructor(name: string) {
        this.name = name;
        this.series = [];
    }
}

export class SplitResultChartSettings {
    yScaleMin: number;
    yScaleMax: number;

    constructor(yScaleMin: number, yScaleMax: number) {
        this.yScaleMin = yScaleMin;
        this.yScaleMax = yScaleMax;
    }
}

@Component({
    selector: 'app-athlete-activity-result-summary',
    templateUrl: './athlete-activity-result-summary.component.html',
    styleUrls: ['./athlete-activity-result-summary.component.scss']
})
export class AthleteActivityResultSummaryComponent implements OnInit {

    readonly SCALE_CONSTANT = 1.2;

    constructor() {
    }

    curves = {
        Basis: shape.curveBasis,
        'Basis Closed': shape.curveBasisClosed,
        Bundle: shape.curveBundle.beta(1),
        Cardinal: shape.curveCardinal,
        'Cardinal Closed': shape.curveCardinalClosed,
        'Catmull Rom': shape.curveCatmullRom,
        'Catmull Rom Closed': shape.curveCatmullRomClosed,
        Linear: shape.curveLinear,
        'Linear Closed': shape.curveLinearClosed,
        'Monotone X': shape.curveMonotoneX,
        'Monotone Y': shape.curveMonotoneY,
        Natural: shape.curveNatural,
        Step: shape.curveStep,
        'Step After': shape.curveStepAfter,
        'Step Before': shape.curveStepBefore,
        default: shape.curveLinear
    };

    private _activityResultSummary: AthleteActivityResultSummary;
    @Input()
    get activityResultSummary(): AthleteActivityResultSummary {
        return this._activityResultSummary;
    }

    set activityResultSummary(value: AthleteActivityResultSummary) {
        if (value) {
            value.resultSplitSummaries = ActivityResultSplit.sortSummariesByActivityResultSplitValue(value.resultSplitSummaries);
            this._activityResultSummary = value;
        }
    }


    statsTableItems: Array<StatsTableItem>;
    splitStatsTableItems: Array<StatsTableItem>;

    colorScheme: any;

    cardsData: any;

    splitResultsChartData = new Array<SplitResultChartData>();

    splitResultsChartSettings: SplitResultChartSettings;

    get unit(): string {
        return this._activityResultSummary.activityResult.resultUnit.abbreviation;
    }

    ngOnInit() {
        this.colorScheme = colorSets.find(s => s.name === 'cool');
        this.statsTableItems = StatsTableItem.createResultsStatsTableItems(this._activityResultSummary);
        this.splitStatsTableItems = StatsTableItem.createSplitStatsTableItems(this._activityResultSummary);
        this.cardsData = [
            {
                name: 'Výsledek',
                value: this._activityResultSummary.athleteActivityResult.value,
                extra: {
                    unit: this._activityResultSummary.activityResult.resultUnit.abbreviation,
                }
            },
            {
                name: 'Výsledek v %',
                value: this._activityResultSummary.stats.athleteStats.valueRankInPercents,
                extra: {
                    unit: '%',
                }
            }
        ];
        this.setSplitResultsChartData(this._activityResultSummary);

    }

    setSplitResultsChartData(activityResultSummary: AthleteActivityResultSummary) {
        const valueSplitResults = new SplitResultChartData(activityResultSummary.activityResult.name);
        const compareValueSplitResults = new SplitResultChartData(activityResultSummary.activityResult.name);
        this.splitResultsChartData = [];

        let yMinScale = Number.MAX_VALUE;
        let yMaxScale = 0;

        for (const resultSplitSummary of activityResultSummary.resultSplitSummaries) {
            if (resultSplitSummary.athleteActivityResultSplit.value) {
                valueSplitResults.series.push({
                    value: resultSplitSummary.athleteActivityResultSplit.value,
                    name: resultSplitSummary.activityResultSplit.splitValue + '. ' + resultSplitSummary.activityResultSplit.splitUnit.name
                });
            }

            if (resultSplitSummary.athleteActivityResultSplit.compareValue) {
                compareValueSplitResults.series.push({
                    value: resultSplitSummary.athleteActivityResultSplit.compareValue,
                    name: resultSplitSummary.activityResultSplit.splitValue + '. ' + resultSplitSummary.activityResultSplit.splitUnit.name
                });
            }

            const currentMinValues = [];
            if (resultSplitSummary.stats.worstValue) {
                currentMinValues.push(resultSplitSummary.stats.worstValue);
            }

            if (resultSplitSummary.stats.worstCompareValue) {
                currentMinValues.push(resultSplitSummary.stats.worstCompareValue);
            }
            const currentMinValue = Math.min(...currentMinValues);
            if (yMinScale > currentMinValue) {
                yMinScale = currentMinValue;
            }

            const currentMaxValues = [];
            currentMaxValues.push(resultSplitSummary.stats.bestValue);
            currentMaxValues.push(resultSplitSummary.stats.bestCompareValue);

            const currentMaxValue = Math.max(...currentMaxValues);
            if (yMaxScale < currentMaxValue) {
                yMaxScale = currentMaxValue;
            }
        }


        this.splitResultsChartData.push(valueSplitResults);
        this.splitResultsChartData.push(compareValueSplitResults);


        if (ResultType.MORE_IS_BETTER.equals(this._activityResultSummary.activityResult.resultType)) {
            this.splitResultsChartSettings = new SplitResultChartSettings(yMinScale / this.SCALE_CONSTANT, yMaxScale * this.SCALE_CONSTANT);
        } else {
            this.splitResultsChartSettings = new SplitResultChartSettings(yMaxScale / this.SCALE_CONSTANT, yMinScale * this.SCALE_CONSTANT);
        }
    }


    showResultLabelHeader(): boolean {
        if (this.statsTableItems) {
            return this.statsTableItems.length > 1;
        }
    }

    showSplitResultLabelHeader(): boolean {
        if (this.splitStatsTableItems) {
            return this.splitStatsTableItems.some(ti => ti !== null && !!ti.label);
        }
    }

    showSplitResultSplitLabelHeader(): boolean {
        if (this.splitStatsTableItems) {
            return this.splitStatsTableItems.some(ti => ti !== null && !!ti.splitLabel);
        }
    }


    selectCard($event) {

    }

    activateCard($event) {

    }

    deactivateCard($event) {

    }

    onLegendLabelClick($event) {

    }

    valueFormat(c): string {
        if (c.data && c.data.extra) {
            return c.value + ' ' + c.data.extra.unit;
        }
        return c.value;
    }


    select($event: any) {

    }
}

