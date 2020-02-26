import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IActivityResult, ActivityResult } from 'app/shared/model/activity-result.model';
import { ActivityResultService } from './activity-result.service';
import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from 'app/entities/unit/unit.service';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from 'app/entities/activity/activity.service';

type SelectableEntity = IUnit | IActivity;

@Component({
  selector: 'jhi-activity-result-update',
  templateUrl: './activity-result-update.component.html'
})
export class ActivityResultUpdateComponent implements OnInit {
  isSaving = false;
  units: IUnit[] = [];
  activities: IActivity[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    resultType: [],
    ratingWeight: [],
    resultUnitId: [],
    activityId: []
  });

  constructor(
    protected activityResultService: ActivityResultService,
    protected unitService: UnitService,
    protected activityService: ActivityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activityResult }) => {
      this.updateForm(activityResult);

      this.unitService.query().subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body || []));

      this.activityService.query().subscribe((res: HttpResponse<IActivity[]>) => (this.activities = res.body || []));
    });
  }

  updateForm(activityResult: IActivityResult): void {
    this.editForm.patchValue({
      id: activityResult.id,
      name: activityResult.name,
      resultType: activityResult.resultType,
      ratingWeight: activityResult.ratingWeight,
      resultUnitId: activityResult.resultUnitId,
      activityId: activityResult.activityId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const activityResult = this.createFromForm();
    if (activityResult.id !== undefined) {
      this.subscribeToSaveResponse(this.activityResultService.update(activityResult));
    } else {
      this.subscribeToSaveResponse(this.activityResultService.create(activityResult));
    }
  }

  private createFromForm(): IActivityResult {
    return {
      ...new ActivityResult(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      resultType: this.editForm.get(['resultType'])!.value,
      ratingWeight: this.editForm.get(['ratingWeight'])!.value,
      resultUnitId: this.editForm.get(['resultUnitId'])!.value,
      activityId: this.editForm.get(['activityId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivityResult>>): void {
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
}
