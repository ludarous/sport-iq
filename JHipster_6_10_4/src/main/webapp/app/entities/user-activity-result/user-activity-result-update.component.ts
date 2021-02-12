import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserActivityResult, UserActivityResult } from 'app/shared/model/user-activity-result.model';
import { UserActivityResultService } from './user-activity-result.service';
import { IActivityResult } from 'app/shared/model/activity-result.model';
import { ActivityResultService } from 'app/entities/activity-result/activity-result.service';
import { IUserActivity } from 'app/shared/model/user-activity.model';
import { UserActivityService } from 'app/entities/user-activity/user-activity.service';

type SelectableEntity = IActivityResult | IUserActivity;

@Component({
  selector: 'jhi-user-activity-result-update',
  templateUrl: './user-activity-result-update.component.html',
})
export class UserActivityResultUpdateComponent implements OnInit {
  isSaving = false;
  activityresults: IActivityResult[] = [];
  useractivities: IUserActivity[] = [];

  editForm = this.fb.group({
    id: [],
    value: [],
    compareValue: [],
    activityResultId: [null, Validators.required],
    userActivityId: [],
  });

  constructor(
    protected userActivityResultService: UserActivityResultService,
    protected activityResultService: ActivityResultService,
    protected userActivityService: UserActivityService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userActivityResult }) => {
      this.updateForm(userActivityResult);

      this.activityResultService.query().subscribe((res: HttpResponse<IActivityResult[]>) => (this.activityresults = res.body || []));

      this.userActivityService.query().subscribe((res: HttpResponse<IUserActivity[]>) => (this.useractivities = res.body || []));
    });
  }

  updateForm(userActivityResult: IUserActivityResult): void {
    this.editForm.patchValue({
      id: userActivityResult.id,
      value: userActivityResult.value,
      compareValue: userActivityResult.compareValue,
      activityResultId: userActivityResult.activityResultId,
      userActivityId: userActivityResult.userActivityId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userActivityResult = this.createFromForm();
    if (userActivityResult.id !== undefined) {
      this.subscribeToSaveResponse(this.userActivityResultService.update(userActivityResult));
    } else {
      this.subscribeToSaveResponse(this.userActivityResultService.create(userActivityResult));
    }
  }

  private createFromForm(): IUserActivityResult {
    return {
      ...new UserActivityResult(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      compareValue: this.editForm.get(['compareValue'])!.value,
      activityResultId: this.editForm.get(['activityResultId'])!.value,
      userActivityId: this.editForm.get(['userActivityId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserActivityResult>>): void {
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
