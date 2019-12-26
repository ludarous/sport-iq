import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IActivityCategory, ActivityCategory } from 'app/shared/model/activity-category.model';
import { ActivityCategoryService } from './activity-category.service';

@Component({
  selector: 'jhi-activity-category-update',
  templateUrl: './activity-category-update.component.html'
})
export class ActivityCategoryUpdateComponent implements OnInit {
  isSaving: boolean;

  activitycategories: IActivityCategory[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    parentActivityCategoryId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected activityCategoryService: ActivityCategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ activityCategory }) => {
      this.updateForm(activityCategory);
    });
    this.activityCategoryService
      .query()
      .subscribe(
        (res: HttpResponse<IActivityCategory[]>) => (this.activitycategories = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(activityCategory: IActivityCategory) {
    this.editForm.patchValue({
      id: activityCategory.id,
      name: activityCategory.name,
      description: activityCategory.description,
      parentActivityCategoryId: activityCategory.parentActivityCategoryId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
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
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      parentActivityCategoryId: this.editForm.get(['parentActivityCategoryId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivityCategory>>) {
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

  trackActivityCategoryById(index: number, item: IActivityCategory) {
    return item.id;
  }
}
