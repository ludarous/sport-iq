import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IActivityCategory, ActivityCategory } from 'app/shared/model/activity-category.model';
import { ActivityCategoryService } from './activity-category.service';

@Component({
  selector: 'jhi-activity-category-update',
  templateUrl: './activity-category-update.component.html'
})
export class ActivityCategoryUpdateComponent implements OnInit {
  isSaving = false;
  activitycategories: IActivityCategory[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    parentActivityCategoryId: []
  });

  constructor(
    protected activityCategoryService: ActivityCategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activityCategory }) => {
      this.updateForm(activityCategory);

      this.activityCategoryService
        .query()
        .subscribe((res: HttpResponse<IActivityCategory[]>) => (this.activitycategories = res.body || []));
    });
  }

  updateForm(activityCategory: IActivityCategory): void {
    this.editForm.patchValue({
      id: activityCategory.id,
      name: activityCategory.name,
      description: activityCategory.description,
      parentActivityCategoryId: activityCategory.parentActivityCategoryId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const activityCategory = this.createFromForm();
    if (activityCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.activityCategoryService.update(activityCategory));
    } else {
      this.subscribeToSaveResponse(this.activityCategoryService.create(activityCategory));
    }
  }

  private createFromForm(): IActivityCategory {
    return {
      ...new ActivityCategory(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      parentActivityCategoryId: this.editForm.get(['parentActivityCategoryId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivityCategory>>): void {
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

  trackById(index: number, item: IActivityCategory): any {
    return item.id;
  }
}
