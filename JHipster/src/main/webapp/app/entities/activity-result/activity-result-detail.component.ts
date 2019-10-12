import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActivityResult } from 'app/shared/model/activity-result.model';

@Component({
    selector: 'jhi-activity-result-detail',
    templateUrl: './activity-result-detail.component.html'
})
export class ActivityResultDetailComponent implements OnInit {
    activityResult: IActivityResult;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ activityResult }) => {
            this.activityResult = activityResult;
        });
    }

    previousState() {
        window.history.back();
    }
}
