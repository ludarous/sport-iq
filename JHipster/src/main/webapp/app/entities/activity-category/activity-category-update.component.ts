import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IActivityCategory } from 'app/shared/model/activity-category.model';
import { ActivityCategoryService } from './activity-category.service';

@Component({
    selector: 'jhi-activity-category-update',
    templateUrl: './activity-category-update.component.html'
})
export class ActivityCategoryUpdateComponent implements OnInit {
    private _activityCategory: IActivityCategory;
    isSaving: boolean;

    activitycategories: IActivityCategory[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private activityCategoryService: ActivityCategoryService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ activityCategory }) => {
            this.activityCategory = activityCategory;
        });
        this.activityCategoryService.query().subscribe(
            (res: HttpResponse<IActivityCategory[]>) => {
                this.activitycategories = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackActivityCategoryById(index: number, item: IActivityCategory) {
        return item.id;
    }
    get activityCategory() {
        return this._activityCategory;
    }

    set activityCategory(activityCategory: IActivityCategory) {
        this._activityCategory = activityCategory;
    }
}
