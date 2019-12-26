import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IAthleteActivityResultSplit, AthleteActivityResultSplit } from 'app/shared/model/athlete-activity-result-split.model';
import { AthleteActivityResultSplitService } from './athlete-activity-result-split.service';
import { IAthleteActivityResult } from 'app/shared/model/athlete-activity-result.model';
import { AthleteActivityResultService } from 'app/entities/athlete-activity-result/athlete-activity-result.service';
import { IActivityResultSplit } from 'app/shared/model/activity-result-split.model';
import { ActivityResultSplitService } from 'app/entities/activity-result-split/activity-result-split.service';

@Component({
  selector: 'jhi-athlete-activity-result-split-update',
  templateUrl: './athlete-activity-result-split-update.component.html'
})
export class AthleteActivityResultSplitUpdateComponent implements OnInit {
  isSaving: boolean;

  athleteactivityresults: IAthleteActivityResult[];

  activityresultsplits: IActivityResultSplit[];

  editForm = this.fb.group({
    id: [],
    value: [],
    compareValue: [],
    athleteActivityResultId: [],
    activityResultSplitId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected athleteActivityResultSplitService: AthleteActivityResultSplitService,
    protected athleteActivityResultService: AthleteActivityResultService,
    protected activityResultSplitService: ActivityResultSplitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ athleteActivityResultSplit }) => {
      this.updateForm(athleteActivityResultSplit);
    });
    this.athleteActivityResultService
      .query()
      .subscribe(
        (res: HttpResponse<IAthleteActivityResult[]>) => (this.athleteactivityresults = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.activityResultSplitService
      .query()
      .subscribe(
        (res: HttpResponse<IActivityResultSplit[]>) => (this.activityresultsplits = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(athleteActivityResultSplit: IAthleteActivityResultSplit) {
    this.editForm.patchValue({
      id: athleteActivityResultSplit.id,
      value: athleteActivityResultSplit.value,
      compareValue: athleteActivityResultSplit.compareValue,
      athleteActivityResultId: athleteActivityResultSplit.athleteActivityResultId,
      activityResultSplitId: athleteActivityResultSplit.activityResultSplitId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
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
      id: this.editForm.get(['id']).value,
      value: this.editForm.get(['value']).value,
      compareValue: this.editForm.get(['compareValue']).value,
      athleteActivityResultId: this.editForm.get(['athleteActivityResultId']).value,
      activityResultSplitId: this.editForm.get(['activityResultSplitId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAthleteActivityResultSplit>>) {
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

  trackAthleteActivityResultById(index: number, item: IAthleteActivityResult) {
    return item.id;
  }

  trackActivityResultSplitById(index: number, item: IActivityResultSplit) {
    return item.id;
  }
}
