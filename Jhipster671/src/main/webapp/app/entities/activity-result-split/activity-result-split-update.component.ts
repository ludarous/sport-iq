import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IActivityResultSplit, ActivityResultSplit } from 'app/shared/model/activity-result-split.model';
import { ActivityResultSplitService } from './activity-result-split.service';
import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from 'app/entities/unit/unit.service';
import { IActivityResult } from 'app/shared/model/activity-result.model';
import { ActivityResultService } from 'app/entities/activity-result/activity-result.service';

type SelectableEntity = IUnit | IActivityResult;

@Component({
  selector: 'jhi-activity-result-split-update',
  templateUrl: './activity-result-split-update.component.html'
})
export class ActivityResultSplitUpdateComponent implements OnInit {
  isSaving = false;
  units: IUnit[] = [];
  activityresults: IActivityResult[] = [];

  editForm = this.fb.group({
    id: [],
    splitValue: [],
    splitUnitId: [],
    activityResultId: []
  });

  constructor(
    protected activityResultSplitService: ActivityResultSplitService,
    protected unitService: UnitService,
    protected activityResultService: ActivityResultService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activityResultSplit }) => {
      this.updateForm(activityResultSplit);

      this.unitService.query().subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body || []));

      this.activityResultService.query().subscribe((res: HttpResponse<IActivityResult[]>) => (this.activityresults = res.body || []));
    });
  }

  updateForm(activityResultSplit: IActivityResultSplit): void {
    this.editForm.patchValue({
      id: activityResultSplit.id,
      splitValue: activityResultSplit.splitValue,
      splitUnitId: activityResultSplit.splitUnitId,
      activityResultId: activityResultSplit.activityResultId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
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
      id: this.editForm.get(['id'])!.value,
      splitValue: this.editForm.get(['splitValue'])!.value,
      splitUnitId: this.editForm.get(['splitUnitId'])!.value,
      activityResultId: this.editForm.get(['activityResultId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivityResultSplit>>): void {
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
