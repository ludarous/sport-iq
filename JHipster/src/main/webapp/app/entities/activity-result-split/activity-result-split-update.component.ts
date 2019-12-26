import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IActivityResultSplit, ActivityResultSplit } from 'app/shared/model/activity-result-split.model';
import { ActivityResultSplitService } from './activity-result-split.service';
import { IActivityResult } from 'app/shared/model/activity-result.model';
import { ActivityResultService } from 'app/entities/activity-result/activity-result.service';
import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from 'app/entities/unit/unit.service';

@Component({
  selector: 'jhi-activity-result-split-update',
  templateUrl: './activity-result-split-update.component.html'
})
export class ActivityResultSplitUpdateComponent implements OnInit {
  isSaving: boolean;

  activityresults: IActivityResult[];

  units: IUnit[];

  editForm = this.fb.group({
    id: [],
    splitValue: [],
    activityResultId: [],
    splitUnitId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected activityResultSplitService: ActivityResultSplitService,
    protected activityResultService: ActivityResultService,
    protected unitService: UnitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ activityResultSplit }) => {
      this.updateForm(activityResultSplit);
    });
    this.activityResultService
      .query()
      .subscribe(
        (res: HttpResponse<IActivityResult[]>) => (this.activityresults = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.unitService
      .query()
      .subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(activityResultSplit: IActivityResultSplit) {
    this.editForm.patchValue({
      id: activityResultSplit.id,
      splitValue: activityResultSplit.splitValue,
      activityResultId: activityResultSplit.activityResultId,
      splitUnitId: activityResultSplit.splitUnitId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const activityResultSplit = this.createFromForm();
    if (activityResultSplit.id !== undefined) {
      this.subscribeToSaveResponse(this.activityResultSplitService.update(activityResultSplit));
    } else {
      this.subscribeToSaveResponse(this.activityResultSplitService.create(activityResultSplit));
    }
  }

  private createFromForm(): IActivityResultSplit {
    return {
      ...new ActivityResultSplit(),
      id: this.editForm.get(['id']).value,
      splitValue: this.editForm.get(['splitValue']).value,
      activityResultId: this.editForm.get(['activityResultId']).value,
      splitUnitId: this.editForm.get(['splitUnitId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivityResultSplit>>) {
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

  trackActivityResultById(index: number, item: IActivityResult) {
    return item.id;
  }

  trackUnitById(index: number, item: IUnit) {
    return item.id;
  }
}
