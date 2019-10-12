import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAthleteActivityResult } from 'app/shared/model/athlete-activity-result.model';
import { AthleteActivityResultService } from './athlete-activity-result.service';
import { IAthleteActivity } from 'app/shared/model/athlete-activity.model';
import { AthleteActivityService } from 'app/entities/athlete-activity';
import { IActivityResult } from 'app/shared/model/activity-result.model';
import { ActivityResultService } from 'app/entities/activity-result';

@Component({
    selector: 'jhi-athlete-activity-result-update',
    templateUrl: './athlete-activity-result-update.component.html'
})
export class AthleteActivityResultUpdateComponent implements OnInit {
    private _athleteActivityResult: IAthleteActivityResult;
    isSaving: boolean;

    athleteactivities: IAthleteActivity[];

    activityresults: IActivityResult[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private athleteActivityResultService: AthleteActivityResultService,
        private athleteActivityService: AthleteActivityService,
        private activityResultService: ActivityResultService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ athleteActivityResult }) => {
            this.athleteActivityResult = athleteActivityResult;
        });
        this.athleteActivityService.query().subscribe(
            (res: HttpResponse<IAthleteActivity[]>) => {
                this.athleteactivities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.activityResultService.query().subscribe(
            (res: HttpResponse<IActivityResult[]>) => {
                this.activityresults = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.athleteActivityResult.id !== undefined) {
            this.subscribeToSaveResponse(this.athleteActivityResultService.update(this.athleteActivityResult));
        } else {
            this.subscribeToSaveResponse(this.athleteActivityResultService.create(this.athleteActivityResult));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAthleteActivityResult>>) {
        result.subscribe(
            (res: HttpResponse<IAthleteActivityResult>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
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

    trackAthleteActivityById(index: number, item: IAthleteActivity) {
        return item.id;
    }

    trackActivityResultById(index: number, item: IActivityResult) {
        return item.id;
    }
    get athleteActivityResult() {
        return this._athleteActivityResult;
    }

    set athleteActivityResult(athleteActivityResult: IAthleteActivityResult) {
        this._athleteActivityResult = athleteActivityResult;
    }
}
