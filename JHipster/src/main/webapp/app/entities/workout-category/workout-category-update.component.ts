import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IWorkoutCategory } from 'app/shared/model/workout-category.model';
import { WorkoutCategoryService } from './workout-category.service';

@Component({
    selector: 'jhi-workout-category-update',
    templateUrl: './workout-category-update.component.html'
})
export class WorkoutCategoryUpdateComponent implements OnInit {
    private _workoutCategory: IWorkoutCategory;
    isSaving: boolean;

    constructor(private workoutCategoryService: WorkoutCategoryService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ workoutCategory }) => {
            this.workoutCategory = workoutCategory;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.workoutCategory.id !== undefined) {
            this.subscribeToSaveResponse(this.workoutCategoryService.update(this.workoutCategory));
        } else {
            this.subscribeToSaveResponse(this.workoutCategoryService.create(this.workoutCategory));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IWorkoutCategory>>) {
        result.subscribe((res: HttpResponse<IWorkoutCategory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get workoutCategory() {
        return this._workoutCategory;
    }

    set workoutCategory(workoutCategory: IWorkoutCategory) {
        this._workoutCategory = workoutCategory;
    }
}
