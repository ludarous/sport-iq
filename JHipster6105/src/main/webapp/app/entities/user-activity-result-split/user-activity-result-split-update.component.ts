import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserActivityResultSplit, UserActivityResultSplit } from 'app/shared/model/user-activity-result-split.model';
import { UserActivityResultSplitService } from './user-activity-result-split.service';
import { IActivityResultSplit } from 'app/shared/model/activity-result-split.model';
import { ActivityResultSplitService } from 'app/entities/activity-result-split/activity-result-split.service';
import { IUserActivityResult } from 'app/shared/model/user-activity-result.model';
import { UserActivityResultService } from 'app/entities/user-activity-result/user-activity-result.service';

type SelectableEntity = IActivityResultSplit | IUserActivityResult;

@Component({
  selector: 'jhi-user-activity-result-split-update',
  templateUrl: './user-activity-result-split-update.component.html',
})
export class UserActivityResultSplitUpdateComponent implements OnInit {
  isSaving = false;
  activityresultsplits: IActivityResultSplit[] = [];
  useractivityresults: IUserActivityResult[] = [];

  editForm = this.fb.group({
    id: [],
    value: [],
    compareValue: [],
    activityResultSplit: [null, Validators.required],
    userActivityResult: [],
  });

  constructor(
    protected userActivityResultSplitService: UserActivityResultSplitService,
    protected activityResultSplitService: ActivityResultSplitService,
    protected userActivityResultService: UserActivityResultService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userActivityResultSplit }) => {
      this.updateForm(userActivityResultSplit);

      this.activityResultSplitService
        .query()
        .subscribe((res: HttpResponse<IActivityResultSplit[]>) => (this.activityresultsplits = res.body || []));

      this.userActivityResultService
        .query()
        .subscribe((res: HttpResponse<IUserActivityResult[]>) => (this.useractivityresults = res.body || []));
    });
  }

  updateForm(userActivityResultSplit: IUserActivityResultSplit): void {
    this.editForm.patchValue({
      id: userActivityResultSplit.id,
      value: userActivityResultSplit.value,
      compareValue: userActivityResultSplit.compareValue,
      activityResultSplit: userActivityResultSplit.activityResultSplit,
      userActivityResult: userActivityResultSplit.userActivityResult,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userActivityResultSplit = this.createFromForm();
    if (userActivityResultSplit.id !== undefined) {
      this.subscribeToSaveResponse(this.userActivityResultSplitService.update(userActivityResultSplit));
    } else {
      this.subscribeToSaveResponse(this.userActivityResultSplitService.create(userActivityResultSplit));
    }
  }

  private createFromForm(): IUserActivityResultSplit {
    return {
      ...new UserActivityResultSplit(),
      id: this.editForm.get(['id'])!.value,
      value: this.editForm.get(['value'])!.value,
      compareValue: this.editForm.get(['compareValue'])!.value,
      activityResultSplit: this.editForm.get(['activityResultSplit'])!.value,
      userActivityResult: this.editForm.get(['userActivityResult'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserActivityResultSplit>>): void {
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
