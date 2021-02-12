import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUserProperties, UserProperties } from 'app/shared/model/user-properties.model';
import { UserPropertiesService } from './user-properties.service';

@Component({
  selector: 'jhi-user-properties-update',
  templateUrl: './user-properties-update.component.html',
})
export class UserPropertiesUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    birthDate: [],
    phone: [],
    nationality: [],
    sex: [],
  });

  constructor(protected userPropertiesService: UserPropertiesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userProperties }) => {
      if (!userProperties.id) {
        const today = moment().startOf('day');
        userProperties.birthDate = today;
      }

      this.updateForm(userProperties);
    });
  }

  updateForm(userProperties: IUserProperties): void {
    this.editForm.patchValue({
      id: userProperties.id,
      birthDate: userProperties.birthDate ? userProperties.birthDate.format(DATE_TIME_FORMAT) : null,
      phone: userProperties.phone,
      nationality: userProperties.nationality,
      sex: userProperties.sex,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userProperties = this.createFromForm();
    if (userProperties.id !== undefined) {
      this.subscribeToSaveResponse(this.userPropertiesService.update(userProperties));
    } else {
      this.subscribeToSaveResponse(this.userPropertiesService.create(userProperties));
    }
  }

  private createFromForm(): IUserProperties {
    return {
      ...new UserProperties(),
      id: this.editForm.get(['id'])!.value,
      birthDate: this.editForm.get(['birthDate'])!.value ? moment(this.editForm.get(['birthDate'])!.value, DATE_TIME_FORMAT) : undefined,
      phone: this.editForm.get(['phone'])!.value,
      nationality: this.editForm.get(['nationality'])!.value,
      sex: this.editForm.get(['sex'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserProperties>>): void {
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
}
