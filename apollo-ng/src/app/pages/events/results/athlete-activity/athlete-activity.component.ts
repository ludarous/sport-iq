import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {IAthleteActivity} from '../../../../entities/model/athlete-activity.model';
import {IActivity} from '../../../../entities/model/activity.model';
import {IAthleteWorkout} from '../../../../entities/model/athlete-workout.model';
import {IActivityResult} from '../../../../entities/model/activity-result.model';
import {IAthleteActivityResult} from '../../../../entities/model/athlete-activity-result.model';
import {EventResultsService} from '../events-results.service';

@Component({
    selector: 'app-activity-general-result',
    templateUrl: './athlete-activity.component.html',
    styleUrls: ['./athlete-activity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AthleteActivityComponent implements OnInit {


    constructor(private eventResultsService: EventResultsService) {
    }

    get activity(): IActivity {
        return this.eventResultsService.selectedActivity;
    }

    get athleteWorkout(): IAthleteWorkout {
        return this.eventResultsService.selectedAthleteWorkout;
    }

    get athleteActivity(): IAthleteActivity {
        return this.eventResultsService.selectedAthleteActivity;
    }



    private _showCompareValue = false;
    get showCompareValue(): boolean {
        return this._showCompareValue;
    }

    set showCompareValue(value: boolean) {
        this._showCompareValue = value;
    }

    ngOnInit() {

    }

    saveAthleteActivity() {
        this.eventResultsService.saveAthleteActivity();
    }

    getActivityResult(athleteActivityResult: IAthleteActivityResult): IActivityResult {
        return this.eventResultsService.getActivityResult(athleteActivityResult);
    }



    trackByIndex(index: number, obj: any): any {
        return index;
    }

}
