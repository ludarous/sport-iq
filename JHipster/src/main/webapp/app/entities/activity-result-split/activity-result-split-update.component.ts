import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IActivityResultSplit } from 'app/shared/model/activity-result-split.model';
import { ActivityResultSplitService } from './activity-result-split.service';
import { IActivityResult } from 'app/shared/model/activity-result.model';
import { ActivityResultService } from 'app/entities/activity-result';
import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from 'app/entities/unit';

@Component({
    selector: 'jhi-activity-result-split-update',
    templateUrl: './activity-result-split-update.component.html'
})
export class ActivityResultSplitUpdateComponent implements OnInit {
    private _activityResultSplit: IActivityResultSplit;
    isSaving: boolean;

    activityresults: IActivityResult[];

    units: IUnit[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private activityResultSplitService: ActivityResultSplitService,
        private activityResultService: ActivityResultService,
        private unitService: UnitService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ activityResultSplit }) => {
            this.activityResultSplit = activityResultSplit;
        });
        this.activityResultService.query().subscribe(
            (res: HttpResponse<IActivityResult[]>) => {
                this.activityresults = res.body;
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
        if (this.activityResultSplit.id !== undefined) {
            this.subscribeToSaveResponse(this.activityResultSplitService.update(this.activityResultSplit));
        } else {
            this.subscribeToSaveResponse(this.activityResultSplitService.create(this.activityResultSplit));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IActivityResultSplit>>) {
        result.subscribe((res: HttpResponse<IActivityResultSplit>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackActivityResultById(index: number, item: IActivityResult) {
        return item.id;
    }

    trackUnitById(index: number, item: IUnit) {
        return item.id;
    }
    get activityResultSplit() {
        return this._activityResultSplit;
    }

    set activityResultSplit(activityResultSplit: IActivityResultSplit) {
        this._activityResultSplit = activityResultSplit;
    }
}
