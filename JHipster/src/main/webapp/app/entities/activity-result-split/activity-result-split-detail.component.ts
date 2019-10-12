import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActivityResultSplit } from 'app/shared/model/activity-result-split.model';

@Component({
    selector: 'jhi-activity-result-split-detail',
    templateUrl: './activity-result-split-detail.component.html'
})
export class ActivityResultSplitDetailComponent implements OnInit {
    activityResultSplit: IActivityResultSplit;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ activityResultSplit }) => {
            this.activityResultSplit = activityResultSplit;
        });
    }

    previousState() {
        window.history.back();
    }
}
