import {Component, Input, OnInit} from '@angular/core';
import {AthleteActivityResultSummary} from '../../../../entities/summaries/athlete-activity-result-summary';
import {StatsTableItem} from '../../../../entities/summaries/stats';
import {colorSets} from '@swimlane/ngx-charts';

@Component({
    selector: 'app-athlete-activity-result-summary',
    templateUrl: './athlete-activity-result-summary.component.html',
    styleUrls: ['./athlete-activity-result-summary.component.scss']
})
export class AthleteActivityResultSummaryComponent implements OnInit {

    constructor() {
    }

    @Input()
    activityResultSummary: AthleteActivityResultSummary;

    statsTableItems: Array<StatsTableItem>;
    splitStatsTableItems: Array<StatsTableItem>;

    colorScheme: any;

    cardsData: any;

    get unit(): string {
        return this.activityResultSummary.activityResult.resultUnit.abbreviation;
    }

    ngOnInit() {
        this.colorScheme = colorSets.find(s => s.name === 'aqua');
        this.statsTableItems = StatsTableItem.createResultsStatsTableItems(this.activityResultSummary);
        this.splitStatsTableItems = StatsTableItem.createSplitStatsTableItems(this.activityResultSummary);
        this.cardsData = [
            {
                name: 'Výsledek',
                value: this.activityResultSummary.athleteActivityResult.value,
                extra: {
                    unit: this.activityResultSummary.activityResult.resultUnit.abbreviation,
                }
            },
            {
                name: 'Výsledek v %',
                value: this.activityResultSummary.stats.athleteStats.valueRankInPercents,
                extra: {
                    unit: '%',
                }
            }
        ];

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


}

