import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IWorkoutCategory, WorkoutCategory } from 'app/shared/model/workout-category.model';
import { WorkoutCategoryService } from './workout-category.service';

@Component({
  selector: 'jhi-workout-category-update',
  templateUrl: './workout-category-update.component.html'
})
export class WorkoutCategoryUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: []
  });

  constructor(
    protected workoutCategoryService: WorkoutCategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ workoutCategory }) => {
      this.updateForm(workoutCategory);
    });
  }

  updateForm(workoutCategory: IWorkoutCategory) {
    this.editForm.patchValue({
      id: workoutCategory.id,
      name: workoutCategory.name,
      description: workoutCategory.description
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const workoutCategory = this.createFromForm();
    if (workoutCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.workoutCategoryService.update(workoutCategory));
    } else {
      this.subscribeToSaveResponse(this.workoutCategoryService.create(workoutCategory));
    }
  }

  private createFromForm(): IWorkoutCategory {
    return {
      ...new WorkoutCategory(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IWorkoutCategory>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
