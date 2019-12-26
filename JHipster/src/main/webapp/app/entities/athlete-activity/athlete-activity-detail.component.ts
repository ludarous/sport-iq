import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAthleteActivity } from 'app/shared/model/athlete-activity.model';

@Component({
  selector: 'jhi-athlete-activity-detail',
  templateUrl: './athlete-activity-detail.component.html'
})
export class AthleteActivityDetailComponent implements OnInit {
  athleteActivity: IAthleteActivity;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ athleteActivity }) => {
      this.athleteActivity = athleteActivity;
    });
  }

  previousState() {
    window.history.back();
  }
}
