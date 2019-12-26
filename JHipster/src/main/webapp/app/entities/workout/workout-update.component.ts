import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IWorkout, Workout } from 'app/shared/model/workout.model';
import { WorkoutService } from './workout.service';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from 'app/entities/activity/activity.service';
import { IWorkoutCategory } from 'app/shared/model/workout-category.model';
import { WorkoutCategoryService } from 'app/entities/workout-category/workout-category.service';
import { ISport } from 'app/shared/model/sport.model';
import { SportService } from 'app/entities/sport/sport.service';

@Component({
  selector: 'jhi-workout-update',
  templateUrl: './workout-update.component.html'
})
export class WorkoutUpdateComponent implements OnInit {
  isSaving: boolean;

  activities: IActivity[];

  workoutcategories: IWorkoutCategory[];

  sports: ISport[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    activities: [],
    categories: [],
    sports: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected workoutService: WorkoutService,
    protected activityService: ActivityService,
    protected workoutCategoryService: WorkoutCategoryService,
    protected sportService: SportService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ workout }) => {
      this.updateForm(workout);
    });
    this.activityService
      .query()
      .subscribe((res: HttpResponse<IActivity[]>) => (this.activities = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.workoutCategoryService
      .query()
      .subscribe(
        (res: HttpResponse<IWorkoutCategory[]>) => (this.workoutcategories = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.sportService
      .query()
      .subscribe((res: HttpResponse<ISport[]>) => (this.sports = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(workout: IWorkout) {
    this.editForm.patchValue({
      id: workout.id,
      name: workout.name,
      description: workout.description,
      activities: workout.activities,
      categories: workout.categories,
      sports: workout.sports
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
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
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      activities: this.editForm.get(['activities']).value,
      categories: this.editForm.get(['categories']).value,
      sports: this.editForm.get(['sports']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkout>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
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

  getSelected(selectedVals: any[], option: any) {
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
