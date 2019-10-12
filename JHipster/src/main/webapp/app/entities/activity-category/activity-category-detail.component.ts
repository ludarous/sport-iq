import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActivityCategory } from 'app/shared/model/activity-category.model';

@Component({
    selector: 'jhi-activity-category-detail',
    templateUrl: './activity-category-detail.component.html'
})
export class ActivityCategoryDetailComponent implements OnInit {
    activityCategory: IActivityCategory;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ activityCategory }) => {
            this.activityCategory = activityCategory;
        });
    }

    previousState() {
        window.history.back();
    }
}
