import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IAthleteActivityResult, AthleteActivityResult } from 'app/shared/model/athlete-activity-result.model';
import { AthleteActivityResultService } from './athlete-activity-result.service';
import { IAthleteActivity } from 'app/shared/model/athlete-activity.model';
import { AthleteActivityService } from 'app/entities/athlete-activity/athlete-activity.service';
import { IActivityResult } from 'app/shared/model/activity-result.model';
import { ActivityResultService } from 'app/entities/activity-result/activity-result.service';

@Component({
  selector: 'jhi-athlete-activity-result-update',
  templateUrl: './athlete-activity-result-update.component.html'
})
export class AthleteActivityResultUpdateComponent implements OnInit {
  isSaving: boolean;

  athleteactivities: IAthleteActivity[];

  activityresults: IActivityResult[];

  editForm = this.fb.group({
    id: [],
    value: [],
    compareValue: [],
    athleteActivityId: [],
    activityResultId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected athleteActivityResultService: AthleteActivityResultService,
    protected athleteActivityService: AthleteActivityService,
    protected activityResultService: ActivityResultService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ athleteActivityResult }) => {
      this.updateForm(athleteActivityResult);
    });
    this.athleteActivityService
      .query()
      .subscribe(
        (res: HttpResponse<IAthleteActivity[]>) => (this.athleteactivities = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.activityResultService
      .query()
      .subscribe(
        (res: HttpResponse<IActivityResult[]>) => (this.activityresults = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(athleteActivityResult: IAthleteActivityResult) {
    this.editForm.patchValue({
      id: athleteActivityResult.id,
      value: athleteActivityResult.value,
      compareValue: athleteActivityResult.compareValue,
      athleteActivityId: athleteActivityResult.athleteActivityId,
      activityResultId: athleteActivityResult.activityResultId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const athleteActivityResult = this.createFromForm();
    if (athleteActivityResult.id !== undefined) {
      this.subscribeToSaveResponse(this.athleteActivityResultService.update(athleteActivityResult));
    } else {
      this.subscribeToSaveResponse(this.athleteActivityResultService.create(athleteActivityResult));
    }
  }

  private createFromForm(): IAthleteActivityResult {
    return {
      ...new AthleteActivityResult(),
      id: this.editForm.get(['id']).value,
      value: this.editForm.get(['value']).value,
      compareValue: this.editForm.get(['compareValue']).value,
      athleteActivityId: this.editForm.get(['athleteActivityId']).value,
      activityResultId: this.editForm.get(['activityResultId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAthleteActivityResult>>) {
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

  trackAthleteActivityById(index: number, item: IAthleteActivity) {
    return item.id;
  }

  trackActivityResultById(index: number, item: IActivityResult) {
    return item.id;
  }
}
