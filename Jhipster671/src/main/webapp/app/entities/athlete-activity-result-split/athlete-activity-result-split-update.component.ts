import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAthleteActivityResultSplit, AthleteActivityResultSplit } from 'app/shared/model/athlete-activity-result-split.model';
import { AthleteActivityResultSplitService } from './athlete-activity-result-split.service';
import { IActivityResultSplit } from 'app/shared/model/activity-result-split.model';
import { ActivityResultSplitService } from 'app/entities/activity-result-split/activity-result-split.service';
import { IAthleteActivityResult } from 'app/shared/model/athlete-activity-result.model';
import { AthleteActivityResultService } from 'app/entities/athlete-activity-result/athlete-activity-result.service';

type SelectableEntity = IActivityResultSplit | IAthleteActivityResult;

@Component({
  selector: 'jhi-athlete-activity-result-split-update',
  templateUrl: './athlete-activity-result-split-update.component.html'
})
export class AthleteActivityResultSplitUpdateComponent implements OnInit {
  isSaving = false;
  activityresultsplits: IActivityResultSplit[] = [];
  athleteactivityresults: IAthleteActivityResult[] = [];

  editForm = this.fb.group({
    id: [],
    value: [],
    compareValue: [],
    activityResultSplitId: [null, Validators.required],
    athleteActivityResultId: []
  });

  constructor(
    protected athleteActivityResultSplitService: AthleteActivityResultSplitService,
    protected activityResultSplitService: ActivityResultSplitService,
    protected athleteActivityResultService: AthleteActivityResultService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ athleteActivityResultSplit }) => {
      this.updateForm(athleteActivityResultSplit);

      this.activityResultSplitService
        .query()
        .subscribe((res: HttpResponse<IActivityResultSplit[]>) => (this.activityresultsplits = res.body || []));

      this.athleteActivityResultService
        .query()
        .subscribe((res: HttpResponse<IAthleteActivityResult[]>) => (this.athleteactivityresults = res.body || []));
    });
  }

  updateForm(athleteActivityResultSplit: IAthleteActivityResultSplit): void {
    this.editForm.patchValue({
      id: athleteActivityResultSplit.id,
      value: athleteActivityResultSplit.value,
      compareValue: athleteActivityResultSplit.compareValue,
      activityResultSplitId: athleteActivityResultSplit.activityResultSplitId,
      athleteActivityResultId: athleteActivityResultSplit.athleteActivityResultId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const athleteActivityResultSplit = this.createFromForm();
    if (athleteActivityResultSplit.id !== undefined) {
      this.subscribeToSaveResponse(this.athleteActivityResultSplitService.update(athleteActivityResultSplit));
    } else {
      this.subscribeToSaveResponse(this.athleteActivityResultSplitService.create(athleteActivityResultSplit));
    }
  }

  private createFromForm(): IAthleteActivityResultSplit {
    return {
      ...new AthleteActivityResultSplit(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      compareValue: this.editForm.get(['compareValue'])!.value,
      activityResultSplitId: this.editForm.get(['activityResultSplitId'])!.value,
      athleteActivityResultId: this.editForm.get(['athleteActivityResultId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAthleteActivityResultSplit>>): void {
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
