import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserActivity } from 'app/shared/model/user-activity.model';

@Component({
  selector: 'jhi-user-activity-detail',
  templateUrl: './user-activity-detail.component.html',
})
export class UserActivityDetailComponent implements OnInit {
  userActivity: IUserActivity | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userActivity }) => (this.userActivity = userActivity));
  }

  previousState(): void {
    window.history.back();
  }
}
