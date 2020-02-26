import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAthlete, Athlete } from 'app/shared/model/athlete.model';
import { AthleteService } from './athlete.service';
import { IAddress } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IAddress | IUser;

@Component({
  selector: 'jhi-athlete-update',
  templateUrl: './athlete-update.component.html'
})
export class AthleteUpdateComponent implements OnInit {
  isSaving = false;
  addresses: IAddress[] = [];
  users: IUser[] = [];

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
    protected athleteService: AthleteService,
    protected addressService: AddressService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ athlete }) => {
      if (!athlete.id) {
        const today = moment().startOf('day');
        athlete.birthDate = today;
      }

      this.updateForm(athlete);

      this.addressService
        .query({ filter: 'athlete-is-null' })
        .pipe(
          map((res: HttpResponse<IAddress[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IAddress[]) => {
          if (!athlete.addressId) {
            this.addresses = resBody;
          } else {
            this.addressService
              .find(athlete.addressId)
              .pipe(
                map((subRes: HttpResponse<IAddress>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IAddress[]) => (this.addresses = concatRes));
          }
        });

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(athlete: IAthlete): void {
    this.editForm.patchValue({
      id: athlete.id,
      firstName: athlete.firstName,
      lastName: athlete.lastName,
      email: athlete.email,
      birthDate: athlete.birthDate ? athlete.birthDate.format(DATE_TIME_FORMAT) : null,
      nationality: athlete.nationality,
      sex: athlete.sex,
      addressId: athlete.addressId,
      userId: athlete.userId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
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
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      email: this.editForm.get(['email'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value ? moment(this.editForm.get(['birthDate'])!.value, DATE_TIME_FORMAT) : undefined,
      nationality: this.editForm.get(['nationality'])!.value,
      sex: this.editForm.get(['sex'])!.value,
      addressId: this.editForm.get(['addressId'])!.value,
      userId: this.editForm.get(['userId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAthlete>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
