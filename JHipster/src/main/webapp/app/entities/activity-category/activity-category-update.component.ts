import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IActivityCategory } from 'app/shared/model/activity-category.model';
import { ActivityCategoryService } from './activity-category.service';

@Component({
    selector: 'jhi-activity-category-update',
    templateUrl: './activity-category-update.component.html'
})
export class ActivityCategoryUpdateComponent implements OnInit {
    private _activityCategory: IActivityCategory;
    isSaving: boolean;

    constructor(private activityCategoryService: ActivityCategoryService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ activityCategory }) => {
            this.activityCategory = activityCategory;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.activityCategory.id !== undefined) {
            this.subscribeToSaveResponse(this.activityCategoryService.update(this.activityCategory));
        } else {
            this.subscribeToSaveResponse(this.activityCategoryService.create(this.activityCategory));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IActivityCategory>>) {
        result.subscribe((res: HttpResponse<IActivityCategory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get activityCategory() {
        return this._activityCategory;
    }

    set activityCategory(activityCategory: IActivityCategory) {
        this._activityCategory = activityCategory;
    }
}
