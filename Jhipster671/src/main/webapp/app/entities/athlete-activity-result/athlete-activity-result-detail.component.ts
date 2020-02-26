import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAthleteActivityResult } from 'app/shared/model/athlete-activity-result.model';

@Component({
  selector: 'jhi-athlete-activity-result-detail',
  templateUrl: './athlete-activity-result-detail.component.html'
})
export class AthleteActivityResultDetailComponent implements OnInit {
  athleteActivityResult: IAthleteActivityResult | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ athleteActivityResult }) => (this.athleteActivityResult = athleteActivityResult));
  }

  previousState(): void {
    window.history.back();
  }
}
