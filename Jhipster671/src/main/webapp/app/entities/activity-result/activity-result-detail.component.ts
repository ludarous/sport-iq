import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActivityResult } from 'app/shared/model/activity-result.model';

@Component({
  selector: 'jhi-activity-result-detail',
  templateUrl: './activity-result-detail.component.html'
})
export class ActivityResultDetailComponent implements OnInit {
  activityResult: IActivityResult | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activityResult }) => (this.activityResult = activityResult));
  }

  previousState(): void {
    window.history.back();
  }
}
