import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserActivityResultSplit } from 'app/shared/model/user-activity-result-split.model';

@Component({
  selector: 'jhi-user-activity-result-split-detail',
  templateUrl: './user-activity-result-split-detail.component.html',
})
export class UserActivityResultSplitDetailComponent implements OnInit {
  userActivityResultSplit: IUserActivityResultSplit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userActivityResultSplit }) => (this.userActivityResultSplit = userActivityResultSplit));
  }

  previousState(): void {
    window.history.back();
  }
}
