import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserActivityResult } from 'app/shared/model/user-activity-result.model';

@Component({
  selector: 'jhi-user-activity-result-detail',
  templateUrl: './user-activity-result-detail.component.html',
})
export class UserActivityResultDetailComponent implements OnInit {
  userActivityResult: IUserActivityResult | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userActivityResult }) => (this.userActivityResult = userActivityResult));
  }

  previousState(): void {
    window.history.back();
  }
}
