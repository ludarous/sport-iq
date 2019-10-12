import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAthleteActivityResultSplit } from 'app/shared/model/athlete-activity-result-split.model';
import { AthleteActivityResultSplitService } from './athlete-activity-result-split.service';
import { IAthleteActivityResult } from 'app/shared/model/athlete-activity-result.model';
import { AthleteActivityResultService } from 'app/entities/athlete-activity-result';

@Component({
    selector: 'jhi-athlete-activity-result-split-update',
    templateUrl: './athlete-activity-result-split-update.component.html'
})
export class AthleteActivityResultSplitUpdateComponent implements OnInit {
    private _athleteActivityResultSplit: IAthleteActivityResultSplit;
    isSaving: boolean;

    athleteactivityresults: IAthleteActivityResult[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private athleteActivityResultSplitService: AthleteActivityResultSplitService,
        private athleteActivityResultService: AthleteActivityResultService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ athleteActivityResultSplit }) => {
            this.athleteActivityResultSplit = athleteActivityResultSplit;
        });
        this.athleteActivityResultService.query().subscribe(
            (res: HttpResponse<IAthleteActivityResult[]>) => {
                this.athleteactivityresults = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.athleteActivityResultSplit.id !== undefined) {
            this.subscribeToSaveResponse(this.athleteActivityResultSplitService.update(this.athleteActivityResultSplit));
        } else {
            this.subscribeToSaveResponse(this.athleteActivityResultSplitService.create(this.athleteActivityResultSplit));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAthleteActivityResultSplit>>) {
        result.subscribe(
            (res: HttpResponse<IAthleteActivityResultSplit>) => this.onSaveSuccess(),
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

    trackAthleteActivityResultById(index: number, item: IAthleteActivityResult) {
        return item.id;
    }
    get athleteActivityResultSplit() {
        return this._athleteActivityResultSplit;
    }

    set athleteActivityResultSplit(athleteActivityResultSplit: IAthleteActivityResultSplit) {
        this._athleteActivityResultSplit = athleteActivityResultSplit;
    }
}
