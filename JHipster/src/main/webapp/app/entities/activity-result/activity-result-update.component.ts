import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IActivityResult } from 'app/shared/model/activity-result.model';
import { ActivityResultService } from './activity-result.service';
import { IActivity } from 'app/shared/model/activity.model';
import { ActivityService } from 'app/entities/activity';
import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from 'app/entities/unit';

@Component({
    selector: 'jhi-activity-result-update',
    templateUrl: './activity-result-update.component.html'
})
export class ActivityResultUpdateComponent implements OnInit {
    private _activityResult: IActivityResult;
    isSaving: boolean;

    activities: IActivity[];

    units: IUnit[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private activityResultService: ActivityResultService,
        private activityService: ActivityService,
        private unitService: UnitService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ activityResult }) => {
            this.activityResult = activityResult;
        });
        this.activityService.query().subscribe(
            (res: HttpResponse<IActivity[]>) => {
                this.activities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.unitService.query().subscribe(
            (res: HttpResponse<IUnit[]>) => {
                this.units = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.activityResult.id !== undefined) {
            this.subscribeToSaveResponse(this.activityResultService.update(this.activityResult));
        } else {
            this.subscribeToSaveResponse(this.activityResultService.create(this.activityResult));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IActivityResult>>) {
        result.subscribe((res: HttpResponse<IActivityResult>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackActivityById(index: number, item: IActivity) {
        return item.id;
    }

    trackUnitById(index: number, item: IUnit) {
        return item.id;
    }
    get activityResult() {
        return this._activityResult;
    }

    set activityResult(activityResult: IActivityResult) {
        this._activityResult = activityResult;
    }
}
