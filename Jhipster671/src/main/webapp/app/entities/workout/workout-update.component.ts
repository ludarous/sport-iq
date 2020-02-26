import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IWorkout, Workout } from 'app/shared/model/workout.model';
import { WorkoutService } from './workout.service';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from 'app/entities/activity/activity.service';
import { IWorkoutCategory } from 'app/shared/model/workout-category.model';
import { WorkoutCategoryService } from 'app/entities/workout-category/workout-category.service';
import { ISport } from 'app/shared/model/sport.model';
import { SportService } from 'app/entities/sport/sport.service';

type SelectableEntity = IActivity | IWorkoutCategory | ISport;

@Component({
  selector: 'jhi-workout-update',
  templateUrl: './workout-update.component.html'
})
export class WorkoutUpdateComponent implements OnInit {
  isSaving = false;
  activities: IActivity[] = [];
  workoutcategories: IWorkoutCategory[] = [];
  sports: ISport[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    activities: [],
    categories: [],
    sports: []
  });

  constructor(
    protected workoutService: WorkoutService,
    protected activityService: ActivityService,
    protected workoutCategoryService: WorkoutCategoryService,
    protected sportService: SportService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ workout }) => {
      this.updateForm(workout);

      this.activityService.query().subscribe((res: HttpResponse<IActivity[]>) => (this.activities = res.body || []));

      this.workoutCategoryService.query().subscribe((res: HttpResponse<IWorkoutCategory[]>) => (this.workoutcategories = res.body || []));

      this.sportService.query().subscribe((res: HttpResponse<ISport[]>) => (this.sports = res.body || []));
    });
  }

  updateForm(workout: IWorkout): void {
    this.editForm.patchValue({
      id: workout.id,
      name: workout.name,
      description: workout.description,
      activities: workout.activities,
      categories: workout.categories,
      sports: workout.sports
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const workout = this.createFromForm();
    if (workout.id !== undefined) {
      this.subscribeToSaveResponse(this.workoutService.update(workout));
    } else {
      this.subscribeToSaveResponse(this.workoutService.create(workout));
    }
  }

  private createFromForm(): IWorkout {
    return {
      ...new Workout(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      activities: this.editForm.get(['activities'])!.value,
      categories: this.editForm.get(['categories'])!.value,
      sports: this.editForm.get(['sports'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkout>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: SelectableEntity[], option: SelectableEntity): SelectableEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
