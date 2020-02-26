import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IActivity, Activity } from 'app/shared/model/activity.model';
import { ActivityService } from './activity.service';
import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from 'app/entities/unit/unit.service';
import { IActivityCategory } from 'app/shared/model/activity-category.model';
import { ActivityCategoryService } from 'app/entities/activity-category/activity-category.service';

type SelectableEntity = IUnit | IActivityCategory;

@Component({
  selector: 'jhi-activity-update',
  templateUrl: './activity-update.component.html'
})
export class ActivityUpdateComponent implements OnInit {
  isSaving = false;
  units: IUnit[] = [];
  activitycategories: IActivityCategory[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
    help: [],
    minAge: [],
    maxAge: [],
    targetValue: [],
    targetUnitId: [],
    categories: []
  });

  constructor(
    protected activityService: ActivityService,
    protected unitService: UnitService,
    protected activityCategoryService: ActivityCategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activity }) => {
      this.updateForm(activity);

      this.unitService.query().subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body || []));

      this.activityCategoryService
        .query()
        .subscribe((res: HttpResponse<IActivityCategory[]>) => (this.activitycategories = res.body || []));
    });
  }

  updateForm(activity: IActivity): void {
    this.editForm.patchValue({
      id: activity.id,
      name: activity.name,
      description: activity.description,
      help: activity.help,
      minAge: activity.minAge,
      maxAge: activity.maxAge,
      targetValue: activity.targetValue,
      targetUnitId: activity.targetUnitId,
      categories: activity.categories
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const activity = this.createFromForm();
    if (activity.id !== undefined) {
      this.subscribeToSaveResponse(this.activityService.update(activity));
    } else {
      this.subscribeToSaveResponse(this.activityService.create(activity));
    }
  }

  private createFromForm(): IActivity {
    return {
      ...new Activity(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      help: this.editForm.get(['help'])!.value,
      minAge: this.editForm.get(['minAge'])!.value,
      maxAge: this.editForm.get(['maxAge'])!.value,
      targetValue: this.editForm.get(['targetValue'])!.value,
      targetUnitId: this.editForm.get(['targetUnitId'])!.value,
      categories: this.editForm.get(['categories'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivity>>): void {
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

  getSelected(selectedVals: IActivityCategory[], option: IActivityCategory): IActivityCategory {
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
