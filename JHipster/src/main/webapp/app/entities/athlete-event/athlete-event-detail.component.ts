import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAthleteEvent } from 'app/shared/model/athlete-event.model';

@Component({
    selector: 'jhi-athlete-event-detail',
    templateUrl: './athlete-event-detail.component.html'
})
export class AthleteEventDetailComponent implements OnInit {
    athleteEvent: IAthleteEvent;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ athleteEvent }) => {
            this.athleteEvent = athleteEvent;
        });
    }

    previousState() {
        window.history.back();
    }
}
