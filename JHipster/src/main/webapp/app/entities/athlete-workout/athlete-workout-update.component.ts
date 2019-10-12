import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAthleteWorkout } from 'app/shared/model/athlete-workout.model';
import { AthleteWorkoutService } from './athlete-workout.service';
import { IAthleteEvent } from 'app/shared/model/athlete-event.model';
import { AthleteEventService } from 'app/entities/athlete-event';
import { IWorkout } from 'app/shared/model/workout.model';
import { WorkoutService } from 'app/entities/workout';

@Component({
    selector: 'jhi-athlete-workout-update',
    templateUrl: './athlete-workout-update.component.html'
})
export class AthleteWorkoutUpdateComponent implements OnInit {
    private _athleteWorkout: IAthleteWorkout;
    isSaving: boolean;

    athleteevents: IAthleteEvent[];

    workouts: IWorkout[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private athleteWorkoutService: AthleteWorkoutService,
        private athleteEventService: AthleteEventService,
        private workoutService: WorkoutService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ athleteWorkout }) => {
            this.athleteWorkout = athleteWorkout;
        });
        this.athleteEventService.query().subscribe(
            (res: HttpResponse<IAthleteEvent[]>) => {
                this.athleteevents = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.workoutService.query().subscribe(
            (res: HttpResponse<IWorkout[]>) => {
                this.workouts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.athleteWorkout.id !== undefined) {
            this.subscribeToSaveResponse(this.athleteWorkoutService.update(this.athleteWorkout));
        } else {
            this.subscribeToSaveResponse(this.athleteWorkoutService.create(this.athleteWorkout));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAthleteWorkout>>) {
        result.subscribe((res: HttpResponse<IAthleteWorkout>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAthleteEventById(index: number, item: IAthleteEvent) {
        return item.id;
    }

    trackWorkoutById(index: number, item: IWorkout) {
        return item.id;
    }
    get athleteWorkout() {
        return this._athleteWorkout;
    }

    set athleteWorkout(athleteWorkout: IAthleteWorkout) {
        this._athleteWorkout = athleteWorkout;
    }
}
