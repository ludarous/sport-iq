import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAthlete, Athlete } from 'app/shared/model/athlete.model';
import { AthleteService } from './athlete.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';
import { IEvent } from 'app/shared/model/event.model';
import { EventService } from 'app/entities/event/event.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-athlete-update',
  templateUrl: './athlete-update.component.html'
})
export class AthleteUpdateComponent implements OnInit {
  isSaving: boolean;

  addresses: IAddress[];

  events: IEvent[];

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    firstName: [null, [Validators.required]],
    lastName: [null, [Validators.required]],
    email: [null, [Validators.required]],
    birthDate: [],
    nationality: [],
    sex: [],
    addressId: [],
    userId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected athleteService: AthleteService,
    protected addressService: AddressService,
    protected eventService: EventService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ athlete }) => {
      this.updateForm(athlete);
    });
    this.addressService.query({ filter: 'athlete-is-null' }).subscribe(
      (res: HttpResponse<IAddress[]>) => {
        if (!this.editForm.get('addressId').value) {
          this.addresses = res.body;
        } else {
          this.addressService
            .find(this.editForm.get('addressId').value)
            .subscribe(
              (subRes: HttpResponse<IAddress>) => (this.addresses = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.eventService
      .query()
      .subscribe((res: HttpResponse<IEvent[]>) => (this.events = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.userService
      .query()
      .subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(athlete: IAthlete) {
    this.editForm.patchValue({
      id: athlete.id,
      firstName: athlete.firstName,
      lastName: athlete.lastName,
      email: athlete.email,
      birthDate: athlete.birthDate != null ? athlete.birthDate.format(DATE_TIME_FORMAT) : null,
      nationality: athlete.nationality,
      sex: athlete.sex,
      addressId: athlete.addressId,
      userId: athlete.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const athlete = this.createFromForm();
    if (athlete.id !== undefined) {
      this.subscribeToSaveResponse(this.athleteService.update(athlete));
    } else {
      this.subscribeToSaveResponse(this.athleteService.create(athlete));
    }
  }

  private createFromForm(): IAthlete {
    return {
      ...new Athlete(),
      id: this.editForm.get(['id']).value,
      firstName: this.editForm.get(['firstName']).value,
      lastName: this.editForm.get(['lastName']).value,
      email: this.editForm.get(['email']).value,
      birthDate:
        this.editForm.get(['birthDate']).value != null ? moment(this.editForm.get(['birthDate']).value, DATE_TIME_FORMAT) : undefined,
      nationality: this.editForm.get(['nationality']).value,
      sex: this.editForm.get(['sex']).value,
      addressId: this.editForm.get(['addressId']).value,
      userId: this.editForm.get(['userId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAthlete>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAddressById(index: number, item: IAddress) {
    return item.id;
  }

  trackEventById(index: number, item: IEvent) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
