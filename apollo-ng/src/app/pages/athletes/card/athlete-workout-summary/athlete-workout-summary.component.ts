import {Component, Input, OnInit} from '@angular/core';
import {AthleteWorkoutSummary} from '../../../../entities/summaries/athlete-workout-summary';
import {ArrayUtils} from '../../../../modules/core/utils/array.utils';
import {Activity} from '../../../../entities/model/activity.model';

@Component({
    selector: 'app-athlete-workout-summary',
    templateUrl: './athlete-workout-summary.component.html',
    styleUrls: ['./athlete-workout-summary.component.scss']
})
export class AthleteWorkoutSummaryComponent implements OnInit {


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
            }
            this._workoutSummary = value;
        }
    }

    ngOnInit() {

    }

}
