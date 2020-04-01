import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAthlete, Athlete } from 'app/shared/model/athlete.model';
import { AthleteService } from './athlete.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { ISport } from 'app/shared/model/sport.model';
import { SportService } from 'app/entities/sport/sport.service';

type SelectableEntity = IUser | ISport;

@Component({
  selector: 'jhi-athlete-update',
  templateUrl: './athlete-update.component.html'
})
export class AthleteUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  sports: ISport[] = [];

  editForm = this.fb.group({
    id: [],
    firstName: [null, [Validators.required]],
    lastName: [null, [Validators.required]],
    email: [null, [Validators.required]],
    phone: [],
    birthDate: [],
    nationality: [],
    sex: [],
    country: [],
    city: [],
    street: [],
    zipCode: [],
    handLaterality: [],
    footLaterality: [],
    steppingFoot: [],
    termsAgreement: [],
    gdprAgreement: [],
    photographyAgreement: [],
    medicalFitnessAgreement: [],
    marketingAgreement: [],
    lrFirstName: [],
    lrLastName: [],
    lrEmail: [],
    lrPhone: [],
    profileCompleted: [],
    userId: [],
    sports: []
  });

  constructor(
    protected athleteService: AthleteService,
    protected userService: UserService,
    protected sportService: SportService,
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

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.sportService.query().subscribe((res: HttpResponse<ISport[]>) => (this.sports = res.body || []));
    });
  }

  updateForm(athlete: IAthlete): void {
    this.editForm.patchValue({
      id: athlete.id,
      firstName: athlete.firstName,
      lastName: athlete.lastName,
      email: athlete.email,
      phone: athlete.phone,
      birthDate: athlete.birthDate ? athlete.birthDate.format(DATE_TIME_FORMAT) : null,
      nationality: athlete.nationality,
      sex: athlete.sex,
      country: athlete.country,
      city: athlete.city,
      street: athlete.street,
      zipCode: athlete.zipCode,
      handLaterality: athlete.handLaterality,
      footLaterality: athlete.footLaterality,
      steppingFoot: athlete.steppingFoot,
      termsAgreement: athlete.termsAgreement,
      gdprAgreement: athlete.gdprAgreement,
      photographyAgreement: athlete.photographyAgreement,
      medicalFitnessAgreement: athlete.medicalFitnessAgreement,
      marketingAgreement: athlete.marketingAgreement,
      lrFirstName: athlete.lrFirstName,
      lrLastName: athlete.lrLastName,
      lrEmail: athlete.lrEmail,
      lrPhone: athlete.lrPhone,
      profileCompleted: athlete.profileCompleted,
      userId: athlete.userId,
      sports: athlete.sports
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
      phone: this.editForm.get(['phone'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value ? moment(this.editForm.get(['birthDate'])!.value, DATE_TIME_FORMAT) : undefined,
      nationality: this.editForm.get(['nationality'])!.value,
      sex: this.editForm.get(['sex'])!.value,
      country: this.editForm.get(['country'])!.value,
      city: this.editForm.get(['city'])!.value,
      street: this.editForm.get(['street'])!.value,
      zipCode: this.editForm.get(['zipCode'])!.value,
      handLaterality: this.editForm.get(['handLaterality'])!.value,
      footLaterality: this.editForm.get(['footLaterality'])!.value,
      steppingFoot: this.editForm.get(['steppingFoot'])!.value,
      termsAgreement: this.editForm.get(['termsAgreement'])!.value,
      gdprAgreement: this.editForm.get(['gdprAgreement'])!.value,
      photographyAgreement: this.editForm.get(['photographyAgreement'])!.value,
      medicalFitnessAgreement: this.editForm.get(['medicalFitnessAgreement'])!.value,
      marketingAgreement: this.editForm.get(['marketingAgreement'])!.value,
      lrFirstName: this.editForm.get(['lrFirstName'])!.value,
      lrLastName: this.editForm.get(['lrLastName'])!.value,
      lrEmail: this.editForm.get(['lrEmail'])!.value,
      lrPhone: this.editForm.get(['lrPhone'])!.value,
      profileCompleted: this.editForm.get(['profileCompleted'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      sports: this.editForm.get(['sports'])!.value
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

  getSelected(selectedVals: ISport[], option: ISport): ISport {
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
