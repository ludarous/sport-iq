import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAthleteActivityResultSplit } from 'app/shared/model/athlete-activity-result-split.model';

@Component({
    selector: 'jhi-athlete-activity-result-split-detail',
    templateUrl: './athlete-activity-result-split-detail.component.html'
})
export class AthleteActivityResultSplitDetailComponent implements OnInit {
    athleteActivityResultSplit: IAthleteActivityResultSplit;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ athleteActivityResultSplit }) => {
            this.athleteActivityResultSplit = athleteActivityResultSplit;
        });
    }

    previousState() {
        window.history.back();
    }
}
