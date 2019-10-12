import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IAthlete } from 'app/shared/model/athlete.model';
import { AthleteService } from './athlete.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address';

@Component({
    selector: 'jhi-athlete-update',
    templateUrl: './athlete-update.component.html'
})
export class AthleteUpdateComponent implements OnInit {
    private _athlete: IAthlete;
    isSaving: boolean;

    addresses: IAddress[];
    birthDate: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private athleteService: AthleteService,
        private addressService: AddressService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ athlete }) => {
            this.athlete = athlete;
        });
        this.addressService.query({ filter: 'athlete-is-null' }).subscribe(
            (res: HttpResponse<IAddress[]>) => {
                if (!this.athlete.addressId) {
                    this.addresses = res.body;
                } else {
                    this.addressService.find(this.athlete.addressId).subscribe(
                        (subRes: HttpResponse<IAddress>) => {
                            this.addresses = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.athlete.birthDate = moment(this.birthDate, DATE_TIME_FORMAT);
        if (this.athlete.id !== undefined) {
            this.subscribeToSaveResponse(this.athleteService.update(this.athlete));
        } else {
            this.subscribeToSaveResponse(this.athleteService.create(this.athlete));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAthlete>>) {
        result.subscribe((res: HttpResponse<IAthlete>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get athlete() {
        return this._athlete;
    }

    set athlete(athlete: IAthlete) {
        this._athlete = athlete;
        this.birthDate = moment(athlete.birthDate).format(DATE_TIME_FORMAT);
    }
}
