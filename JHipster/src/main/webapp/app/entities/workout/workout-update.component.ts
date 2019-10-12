import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IWorkout } from 'app/shared/model/workout.model';
import { WorkoutService } from './workout.service';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from 'app/entities/activity';
import { IWorkoutCategory } from 'app/shared/model/workout-category.model';
import { WorkoutCategoryService } from 'app/entities/workout-category';
import { ISport } from 'app/shared/model/sport.model';
import { SportService } from 'app/entities/sport';

@Component({
    selector: 'jhi-workout-update',
    templateUrl: './workout-update.component.html'
})
export class WorkoutUpdateComponent implements OnInit {
    private _workout: IWorkout;
    isSaving: boolean;

    activities: IActivity[];

    workoutcategories: IWorkoutCategory[];

    sports: ISport[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private workoutService: WorkoutService,
        private activityService: ActivityService,
        private workoutCategoryService: WorkoutCategoryService,
        private sportService: SportService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ workout }) => {
            this.workout = workout;
        });
        this.activityService.query().subscribe(
            (res: HttpResponse<IActivity[]>) => {
                this.activities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.workoutCategoryService.query().subscribe(
            (res: HttpResponse<IWorkoutCategory[]>) => {
                this.workoutcategories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.sportService.query().subscribe(
            (res: HttpResponse<ISport[]>) => {
                this.sports = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.workout.id !== undefined) {
            this.subscribeToSaveResponse(this.workoutService.update(this.workout));
        } else {
            this.subscribeToSaveResponse(this.workoutService.create(this.workout));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IWorkout>>) {
        result.subscribe((res: HttpResponse<IWorkout>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackActivityById(index: number, item: IActivity) {
        return item.id;
    }

    trackWorkoutCategoryById(index: number, item: IWorkoutCategory) {
        return item.id;
    }

    trackSportById(index: number, item: ISport) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get workout() {
        return this._workout;
    }

    set workout(workout: IWorkout) {
        this._workout = workout;
    }
}
