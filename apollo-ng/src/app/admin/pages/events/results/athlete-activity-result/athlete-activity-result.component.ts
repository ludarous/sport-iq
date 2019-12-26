import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {IActivityResult} from '../../../../entities/model/activity-result.model';
import {IActivityResultSplit} from '../../../../entities/model/activity-result-split.model';
import {IAthleteActivityResult} from '../../../../entities/model/athlete-activity-result.model';
import {IAthleteActivityResultSplit} from '../../../../entities/model/athlete-activity-result-split.model';
import {ActivityResultSettings} from '../athlete-activity/athlete-activity.component';

@Component({
    selector: 'app-activity-result-general-result',
    templateUrl: './athlete-activity-result.component.html',
    styleUrls: ['./athlete-activity-result.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AthleteActivityResultComponent implements OnInit {



    constructor() {
    }

    private _athleteActivityResult: IAthleteActivityResult;
    @Input()
    get athleteActivityResult(): IAthleteActivityResult {
        return this._athleteActivityResult;
    }

    set athleteActivityResult(value: IAthleteActivityResult) {
        if (value) {
            this._athleteActivityResult = value;
            if (this._activityResult) {
                value.athleteActivityResultSplits =
                    this.sortResultSplits(value.athleteActivityResultSplits);
            }
        }
    }

    private _activityResult: IActivityResult;
    @Input()
    get activityResult(): IActivityResult {
        return this._activityResult;
    }

    set activityResult(value: IActivityResult) {
        if (value) {
            this._activityResult = value;
            if (this.athleteActivityResult) {
                this.athleteActivityResult.athleteActivityResultSplits =
                    this.sortResultSplits(this.athleteActivityResult.athleteActivityResultSplits);
            }
        }
    }

    private _activityResultSettings = new ActivityResultSettings();
    @Input()
    get activityResultSettings(): ActivityResultSettings {
        return this._activityResultSettings;
    }

    set activityResultSettings(value: ActivityResultSettings) {
        this._activityResultSettings = value;
    }

    ngOnInit() {
        console.log(this._activityResult);
        if (this.athleteActivityResult.compareValue) {
            this.activityResultSettings.showCompareValue = true;
        }
    }

    getActivityResultSplit(athleteActivityResultSplit: IAthleteActivityResultSplit): IActivityResultSplit {
        return this._activityResult.resultSplits.find(rs => rs.id === athleteActivityResultSplit.activityResultSplitId);
    }

    fix(value: number): string {
        if (value) {
            return value.toFixed(2);
        }
        return '';
    }

    computedDifference(): number {
        if (this.athleteActivityResult.value && this.athleteActivityResult.compareValue) {
            return (this.athleteActivityResult.value - this.athleteActivityResult.compareValue);
        }
        return null;
    }

    computedDifferenceInPercents(): number {
        if (this.athleteActivityResult.value && this.athleteActivityResult.compareValue) {
            const result = (this.computedDifference() / this.athleteActivityResult.value) * 100;
            return result;
        }

        return null;
    }

    sortResultSplits(items: Array<IAthleteActivityResultSplit>, order: number = 1): Array<IAthleteActivityResultSplit> {
        if (items && items.length > 1) {
            const comparer = (o1: IAthleteActivityResultSplit, o2: IAthleteActivityResultSplit) => {
                const aar1 = this.getActivityResultSplit(o1);
                const aar2 = this.getActivityResultSplit(o2);

                const result = aar1.splitValue - aar2.splitValue;
                return result * order;
            };

            return items.sort(comparer);
        }
        return items;
    }

}
