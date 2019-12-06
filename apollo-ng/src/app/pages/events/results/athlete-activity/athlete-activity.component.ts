import {Component, Input, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Location} from '@angular/common';
import {IAthleteActivity} from '../../../../entities/model/athlete-activity.model';
import {IActivity} from '../../../../entities/model/activity.model';
import {AthleteService} from '../../../../services/rest/athlete.service';
import {ToastService} from '../../../../modules/core/services/message.service';
import {EnumTranslatorService} from '../../../../modules/shared-components/services/enum-translator.service';
import {IAthleteWorkout} from '../../../../entities/model/athlete-workout.model';
import {AthleteActivityService} from '../../../../services/rest/athlete-activity.service';
import {IActivityResult} from '../../../../entities/model/activity-result.model';
import {IActivityResultSplit} from '../../../../entities/model/activity-result-split.model';
import {IAthleteActivityResult} from '../../../../entities/model/athlete-activity-result.model';
import {IAthleteActivityResultSplit} from '../../../../entities/model/athlete-activity-result-split.model';

@Component({
    selector: 'app-activity-general-result',
    templateUrl: './athlete-activity.component.html',
    styleUrls: ['./athlete-activity.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AthleteActivityComponent implements OnInit, OnDestroy {


    constructor(private athleteService: AthleteService,
                private athleteActivityService: AthleteActivityService,
                private activatedRoute: ActivatedRoute,
                private toastService: ToastService,
                private enumTranslateService: EnumTranslatorService,
                private formBuilder: FormBuilder,
                private router: Router,
                private location: Location) {
    }

    private _activity: IActivity;
    @Input()
    get activity(): IActivity {
        return this._activity;
    }

    set activity(value: IActivity) {
        this._activity = value;
    }


    private _athleteWorkout: IAthleteWorkout
    @Input()
    get athleteWorkout(): IAthleteWorkout {
        return this._athleteWorkout;
    }

    set athleteWorkout(value: IAthleteWorkout) {
        this._athleteWorkout = value;
    }


    private _athleteActivity: IAthleteActivity;
    @Input()
    get athleteActivity(): IAthleteActivity {
        return this._athleteActivity;
    }

    set athleteActivity(value: IAthleteActivity) {
        this._athleteActivity = value;
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

    ngOnDestroy(): void {
        this.saveAthleteActivity();
    }

    saveAthleteActivity() {
        const athleteActivityToSave = this.athleteActivity;

        let saveAthleteActivity$;
        if (athleteActivityToSave.id) {
            saveAthleteActivity$ = this.athleteActivityService.update(athleteActivityToSave);
        } else {
            saveAthleteActivity$ = this.athleteActivityService.create(athleteActivityToSave);
        }

        saveAthleteActivity$.subscribe(
            (athleteActivityResponse: HttpResponse<IAthleteActivity>) => {
                this._athleteActivity = athleteActivityResponse.body;
                this.toastService.showSuccess('Událost uložena');
            },
            (errorResponse: HttpErrorResponse) => {
                this.toastService.showError('Událost nebyla uložena', errorResponse.error.detail);
            });
    }

    getActivityResult(athleteActivityResult: IAthleteActivityResult): IActivityResult {
        const activityResult = this.activity.activityResults.find(ar => ar.id === athleteActivityResult.activityResultId);
        return activityResult;
    }



    trackByIndex(index: number, obj: any): any {
        return index;
    }

}
