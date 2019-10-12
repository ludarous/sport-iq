import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IEvent } from 'app/shared/model/event.model';
import { EventService } from './event.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address';
import { IWorkout } from 'app/shared/model/workout.model';
import { WorkoutService } from 'app/entities/workout';

@Component({
    selector: 'jhi-event-update',
    templateUrl: './event-update.component.html'
})
export class EventUpdateComponent implements OnInit {
    private _event: IEvent;
    isSaving: boolean;

    addresses: IAddress[];

    workouts: IWorkout[];
    date: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private eventService: EventService,
        private addressService: AddressService,
        private workoutService: WorkoutService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ event }) => {
            this.event = event;
        });
        this.addressService.query().subscribe(
            (res: HttpResponse<IAddress[]>) => {
                this.addresses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.workoutService.query().subscribe(
            (res: HttpResponse<IWorkout[]>) => {
                this.workouts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.event.date = moment(this.date, DATE_TIME_FORMAT);
        if (this.event.id !== undefined) {
            this.subscribeToSaveResponse(this.eventService.update(this.event));
        } else {
            this.subscribeToSaveResponse(this.eventService.create(this.event));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEvent>>) {
        result.subscribe((res: HttpResponse<IEvent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAddressById(index: number, item: IAddress) {
        return item.id;
    }

    trackWorkoutById(index: number, item: IWorkout) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get event() {
        return this._event;
    }

    set event(event: IEvent) {
        this._event = event;
        this.date = moment(event.date).format(DATE_TIME_FORMAT);
    }
}
