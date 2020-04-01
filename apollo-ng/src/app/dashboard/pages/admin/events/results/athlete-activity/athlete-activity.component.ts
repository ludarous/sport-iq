import {Component, Input, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {IAthleteActivity} from '../../../../../entities/model/athlete-activity.model';
import {IActivity} from '../../../../../entities/model/activity.model';
import {IAthleteWorkout} from '../../../../../entities/model/athlete-workout.model';
import {IActivityResult} from '../../../../../entities/model/activity-result.model';
import {IAthleteActivityResult} from '../../../../../entities/model/athlete-activity-result.model';
import {EventResultsService} from '../events-results.service';
import {IAthlete} from '../../../../../entities/model/athlete.model';

export class ActivityResultSettings {
    showCompareValue: boolean;
}

@Component({
    selector: 'app-activity-general-result',
    templateUrl: './athlete-activity.component.html',
    styleUrls: ['./athlete-activity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AthleteActivityComponent implements OnInit {


    constructor(private eventResultsService: EventResultsService) {
    }

    @Input()
    activity: IActivity;

    @Input()
    athlete: IAthlete;

    @Input()
    athleteActivity: IAthleteActivity;

    @Input()
    athleteOrder: number;



    private _activityResultSettings = new ActivityResultSettings();
    get activityResultSettings(): ActivityResultSettings {
        return this._activityResultSettings;
    }

    set activityResultSettings(value: ActivityResultSettings) {
        this._activityResultSettings = value;
    }

    ngOnInit() {

    }

    getHeader() {
        return (this.athleteOrder ? (this.athleteOrder + '. ') : '') +  this.athlete.firstName + ' ' + this.athlete.lastName;
    }

    saveAthleteActivity() {
        this.eventResultsService.saveAthleteActivity(this.athleteActivity).subscribe((athleteActivity: IAthleteActivity) => {
            this.athleteActivity = athleteActivity;
        });
    }

    getActivityResult(athleteActivityResult: IAthleteActivityResult): IActivityResult {
        return this.eventResultsService.getActivityResult(athleteActivityResult);
    }



    trackByIndex(index: number, obj: any): any {
        return index;
    }

}
