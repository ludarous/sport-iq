import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAthleteActivity, AthleteActivity } from 'app/shared/model/athlete-activity.model';
import { AthleteActivityService } from './athlete-activity.service';
import { IAthleteWorkout } from 'app/shared/model/athlete-workout.model';
import { AthleteWorkoutService } from 'app/entities/athlete-workout/athlete-workout.service';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from 'app/entities/activity/activity.service';

@Component({
  selector: 'jhi-athlete-activity-update',
  templateUrl: './athlete-activity-update.component.html'
})
export class AthleteActivityUpdateComponent implements OnInit {
  isSaving: boolean;

  athleteworkouts: IAthleteWorkout[];

  activities: IActivity[];

  editForm = this.fb.group({
    id: [],
    note: [],
    date: [],
    athleteWorkoutId: [],
    activityId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected athleteActivityService: AthleteActivityService,
    protected athleteWorkoutService: AthleteWorkoutService,
    protected activityService: ActivityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ athleteActivity }) => {
      this.updateForm(athleteActivity);
    });
    this.athleteWorkoutService
      .query()
      .subscribe(
        (res: HttpResponse<IAthleteWorkout[]>) => (this.athleteworkouts = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.activityService
      .query()
      .subscribe((res: HttpResponse<IActivity[]>) => (this.activities = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(athleteActivity: IAthleteActivity) {
    this.editForm.patchValue({
      id: athleteActivity.id,
      note: athleteActivity.note,
      date: athleteActivity.date != null ? athleteActivity.date.format(DATE_TIME_FORMAT) : null,
      athleteWorkoutId: athleteActivity.athleteWorkoutId,
      activityId: athleteActivity.activityId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const athleteActivity = this.createFromForm();
    if (athleteActivity.id !== undefined) {
      this.subscribeToSaveResponse(this.athleteActivityService.update(athleteActivity));
    } else {
      this.subscribeToSaveResponse(this.athleteActivityService.create(athleteActivity));
    }
  }

  private createFromForm(): IAthleteActivity {
    return {
      ...new AthleteActivity(),
      id: this.editForm.get(['id']).value,
      note: this.editForm.get(['note']).value,
      date: this.editForm.get(['date']).value != null ? moment(this.editForm.get(['date']).value, DATE_TIME_FORMAT) : undefined,
      athleteWorkoutId: this.editForm.get(['athleteWorkoutId']).value,
      activityId: this.editForm.get(['activityId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAthleteActivity>>) {
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

  trackAthleteWorkoutById(index: number, item: IAthleteWorkout) {
    return item.id;
  }

  trackActivityById(index: number, item: IActivity) {
    return item.id;
  }
}
