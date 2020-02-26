import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAthleteActivityResultSplit } from 'app/shared/model/athlete-activity-result-split.model';

@Component({
  selector: 'jhi-athlete-activity-result-split-detail',
  templateUrl: './athlete-activity-result-split-detail.component.html'
})
export class AthleteActivityResultSplitDetailComponent implements OnInit {
  athleteActivityResultSplit: IAthleteActivityResultSplit | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ athleteActivityResultSplit }) => (this.athleteActivityResultSplit = athleteActivityResultSplit));
  }

  previousState(): void {
    window.history.back();
  }
}
