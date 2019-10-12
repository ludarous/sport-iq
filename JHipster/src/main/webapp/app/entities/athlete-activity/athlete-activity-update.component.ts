import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IAthleteActivity } from 'app/shared/model/athlete-activity.model';
import { AthleteActivityService } from './athlete-activity.service';
import { IAthleteWorkout } from 'app/shared/model/athlete-workout.model';
import { AthleteWorkoutService } from 'app/entities/athlete-workout';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from 'app/entities/activity';

@Component({
    selector: 'jhi-athlete-activity-update',
    templateUrl: './athlete-activity-update.component.html'
})
export class AthleteActivityUpdateComponent implements OnInit {
    private _athleteActivity: IAthleteActivity;
    isSaving: boolean;

    athleteworkouts: IAthleteWorkout[];

    activities: IActivity[];
    date: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private athleteActivityService: AthleteActivityService,
        private athleteWorkoutService: AthleteWorkoutService,
        private activityService: ActivityService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ athleteActivity }) => {
            this.athleteActivity = athleteActivity;
        });
        this.athleteWorkoutService.query().subscribe(
            (res: HttpResponse<IAthleteWorkout[]>) => {
                this.athleteworkouts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.activityService.query().subscribe(
            (res: HttpResponse<IActivity[]>) => {
                this.activities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.athleteActivity.date = moment(this.date, DATE_TIME_FORMAT);
        if (this.athleteActivity.id !== undefined) {
            this.subscribeToSaveResponse(this.athleteActivityService.update(this.athleteActivity));
        } else {
            this.subscribeToSaveResponse(this.athleteActivityService.create(this.athleteActivity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAthleteActivity>>) {
        result.subscribe((res: HttpResponse<IAthleteActivity>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAthleteWorkoutById(index: number, item: IAthleteWorkout) {
        return item.id;
    }

    trackActivityById(index: number, item: IActivity) {
        return item.id;
    }
    get athleteActivity() {
        return this._athleteActivity;
    }

    set athleteActivity(athleteActivity: IAthleteActivity) {
        this._athleteActivity = athleteActivity;
        this.date = moment(athleteActivity.date).format(DATE_TIME_FORMAT);
    }
}
