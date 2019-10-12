import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAthleteEvent } from 'app/shared/model/athlete-event.model';
import { AthleteEventService } from './athlete-event.service';
import { IEvent } from 'app/shared/model/event.model';
import { EventService } from 'app/entities/event';
import { IAthlete } from 'app/shared/model/athlete.model';
import { AthleteService } from 'app/entities/athlete';

@Component({
    selector: 'jhi-athlete-event-update',
    templateUrl: './athlete-event-update.component.html'
})
export class AthleteEventUpdateComponent implements OnInit {
    private _athleteEvent: IAthleteEvent;
    isSaving: boolean;

    events: IEvent[];

    athletes: IAthlete[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private athleteEventService: AthleteEventService,
        private eventService: EventService,
        private athleteService: AthleteService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ athleteEvent }) => {
            this.athleteEvent = athleteEvent;
        });
        this.eventService.query().subscribe(
            (res: HttpResponse<IEvent[]>) => {
                this.events = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.athleteService.query().subscribe(
            (res: HttpResponse<IAthlete[]>) => {
                this.athletes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.athleteEvent.id !== undefined) {
            this.subscribeToSaveResponse(this.athleteEventService.update(this.athleteEvent));
        } else {
            this.subscribeToSaveResponse(this.athleteEventService.create(this.athleteEvent));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAthleteEvent>>) {
        result.subscribe((res: HttpResponse<IAthleteEvent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEventById(index: number, item: IEvent) {
        return item.id;
    }

    trackAthleteById(index: number, item: IAthlete) {
        return item.id;
    }
    get athleteEvent() {
        return this._athleteEvent;
    }

    set athleteEvent(athleteEvent: IAthleteEvent) {
        this._athleteEvent = athleteEvent;
    }
}
