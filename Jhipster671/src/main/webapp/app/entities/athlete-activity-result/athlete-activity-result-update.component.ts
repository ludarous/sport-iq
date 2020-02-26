import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAthleteActivityResult, AthleteActivityResult } from 'app/shared/model/athlete-activity-result.model';
import { AthleteActivityResultService } from './athlete-activity-result.service';
import { IActivityResult } from 'app/shared/model/activity-result.model';
import { ActivityResultService } from 'app/entities/activity-result/activity-result.service';
import { IAthleteActivity } from 'app/shared/model/athlete-activity.model';
import { AthleteActivityService } from 'app/entities/athlete-activity/athlete-activity.service';

type SelectableEntity = IActivityResult | IAthleteActivity;

@Component({
  selector: 'jhi-athlete-activity-result-update',
  templateUrl: './athlete-activity-result-update.component.html'
})
export class AthleteActivityResultUpdateComponent implements OnInit {
  isSaving = false;
  activityresults: IActivityResult[] = [];
  athleteactivities: IAthleteActivity[] = [];

  editForm = this.fb.group({
    id: [],
    value: [],
    compareValue: [],
    activityResultId: [null, Validators.required],
    athleteActivityId: []
  });

  constructor(
    protected athleteActivityResultService: AthleteActivityResultService,
    protected activityResultService: ActivityResultService,
    protected athleteActivityService: AthleteActivityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ athleteActivityResult }) => {
      this.updateForm(athleteActivityResult);

      this.activityResultService.query().subscribe((res: HttpResponse<IActivityResult[]>) => (this.activityresults = res.body || []));

      this.athleteActivityService.query().subscribe((res: HttpResponse<IAthleteActivity[]>) => (this.athleteactivities = res.body || []));
    });
  }

  updateForm(athleteActivityResult: IAthleteActivityResult): void {
    this.editForm.patchValue({
      id: athleteActivityResult.id,
      value: athleteActivityResult.value,
      compareValue: athleteActivityResult.compareValue,
      activityResultId: athleteActivityResult.activityResultId,
      athleteActivityId: athleteActivityResult.athleteActivityId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
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
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      compareValue: this.editForm.get(['compareValue'])!.value,
      activityResultId: this.editForm.get(['activityResultId'])!.value,
      athleteActivityId: this.editForm.get(['athleteActivityId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAthleteActivityResult>>): void {
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
