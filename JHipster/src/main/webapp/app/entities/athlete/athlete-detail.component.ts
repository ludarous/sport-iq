import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAthlete } from 'app/shared/model/athlete.model';

@Component({
    selector: 'jhi-athlete-detail',
    templateUrl: './athlete-detail.component.html'
})
export class AthleteDetailComponent implements OnInit {
    athlete: IAthlete;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ athlete }) => {
            this.athlete = athlete;
        });
    }

    previousState() {
        window.history.back();
    }
}
