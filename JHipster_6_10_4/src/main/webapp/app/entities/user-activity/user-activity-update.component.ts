import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUserActivity, UserActivity } from 'app/shared/model/user-activity.model';
import { UserActivityService } from './user-activity.service';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from 'app/entities/activity/activity.service';
import { IUserEvent } from 'app/shared/model/user-event.model';
import { UserEventService } from 'app/entities/user-event/user-event.service';

type SelectableEntity = IActivity | IUserEvent;

@Component({
  selector: 'jhi-user-activity-update',
  templateUrl: './user-activity-update.component.html',
})
export class UserActivityUpdateComponent implements OnInit {
  isSaving = false;
  activities: IActivity[] = [];
  userevents: IUserEvent[] = [];

  editForm = this.fb.group({
    id: [],
    note: [],
    date: [],
    activityId: [null, Validators.required],
    userEventId: [],
  });

  constructor(
    protected userActivityService: UserActivityService,
    protected activityService: ActivityService,
    protected userEventService: UserEventService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userActivity }) => {
      if (!userActivity.id) {
        const today = moment().startOf('day');
        userActivity.date = today;
      }

      this.updateForm(userActivity);

      this.activityService.query().subscribe((res: HttpResponse<IActivity[]>) => (this.activities = res.body || []));

      this.userEventService.query().subscribe((res: HttpResponse<IUserEvent[]>) => (this.userevents = res.body || []));
    });
  }

  updateForm(userActivity: IUserActivity): void {
    this.editForm.patchValue({
      id: userActivity.id,
      note: userActivity.note,
      date: userActivity.date ? userActivity.date.format(DATE_TIME_FORMAT) : null,
      activityId: userActivity.activityId,
      userEventId: userActivity.userEventId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userActivity = this.createFromForm();
    if (userActivity.id !== undefined) {
      this.subscribeToSaveResponse(this.userActivityService.update(userActivity));
    } else {
      this.subscribeToSaveResponse(this.userActivityService.create(userActivity));
    }
  }

  private createFromForm(): IUserActivity {
    return {
      ...new UserActivity(),
      id: this.editForm.get(['id'])!.value,
      note: this.editForm.get(['note'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      activityId: this.editForm.get(['activityId'])!.value,
      userEventId: this.editForm.get(['userEventId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserActivity>>): void {
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
