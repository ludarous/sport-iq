import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IActivityResult, ActivityResult } from 'app/shared/model/activity-result.model';
import { ActivityResultService } from './activity-result.service';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from 'app/entities/activity/activity.service';
import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from 'app/entities/unit/unit.service';

@Component({
  selector: 'jhi-activity-result-update',
  templateUrl: './activity-result-update.component.html'
})
export class ActivityResultUpdateComponent implements OnInit {
  isSaving: boolean;

  activities: IActivity[];

  units: IUnit[];

  editForm = this.fb.group({
    id: [],
    name: [],
    resultType: [],
    ratingWeight: [],
    activityId: [],
    resultUnitId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected activityResultService: ActivityResultService,
    protected activityService: ActivityService,
    protected unitService: UnitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ activityResult }) => {
      this.updateForm(activityResult);
    });
    this.activityService
      .query()
      .subscribe((res: HttpResponse<IActivity[]>) => (this.activities = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.unitService
      .query()
      .subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(activityResult: IActivityResult) {
    this.editForm.patchValue({
      id: activityResult.id,
      name: activityResult.name,
      resultType: activityResult.resultType,
      ratingWeight: activityResult.ratingWeight,
      activityId: activityResult.activityId,
      resultUnitId: activityResult.resultUnitId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
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
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      resultType: this.editForm.get(['resultType']).value,
      ratingWeight: this.editForm.get(['ratingWeight']).value,
      activityId: this.editForm.get(['activityId']).value,
      resultUnitId: this.editForm.get(['resultUnitId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivityResult>>) {
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

  trackUnitById(index: number, item: IUnit) {
    return item.id;
  }
}
