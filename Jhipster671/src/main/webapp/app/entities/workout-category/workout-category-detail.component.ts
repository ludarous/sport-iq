import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWorkoutCategory } from 'app/shared/model/workout-category.model';

@Component({
  selector: 'jhi-workout-category-detail',
  templateUrl: './workout-category-detail.component.html'
})
export class WorkoutCategoryDetailComponent implements OnInit {
  workoutCategory: IWorkoutCategory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workoutCategory }) => (this.workoutCategory = workoutCategory));
  }

  previousState(): void {
    window.history.back();
  }
}
