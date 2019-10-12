import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkoutCategory } from 'app/shared/model/workout-category.model';

@Component({
    selector: 'jhi-workout-category-detail',
    templateUrl: './workout-category-detail.component.html'
})
export class WorkoutCategoryDetailComponent implements OnInit {
    workoutCategory: IWorkoutCategory;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ workoutCategory }) => {
            this.workoutCategory = workoutCategory;
        });
    }

    previousState() {
        window.history.back();
    }
}
