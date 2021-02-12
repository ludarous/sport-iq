import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IBodyCharacteristics, BodyCharacteristics } from 'app/shared/model/body-characteristics.model';
import { BodyCharacteristicsService } from './body-characteristics.service';
import { IUnit } from 'app/shared/model/unit.model';
import { UnitService } from 'app/entities/unit/unit.service';

@Component({
  selector: 'jhi-body-characteristics-update',
  templateUrl: './body-characteristics-update.component.html',
})
export class BodyCharacteristicsUpdateComponent implements OnInit {
  isSaving = false;
  units: IUnit[] = [];

  editForm = this.fb.group({
    id: [],
    height: [],
    weight: [],
    date: [],
    heightUnitId: [],
    widthUnitId: [],
  });

  constructor(
    protected bodyCharacteristicsService: BodyCharacteristicsService,
    protected unitService: UnitService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bodyCharacteristics }) => {
      if (!bodyCharacteristics.id) {
        const today = moment().startOf('day');
        bodyCharacteristics.date = today;
      }

      this.updateForm(bodyCharacteristics);

      this.unitService.query().subscribe((res: HttpResponse<IUnit[]>) => (this.units = res.body || []));
    });
  }

  updateForm(bodyCharacteristics: IBodyCharacteristics): void {
    this.editForm.patchValue({
      id: bodyCharacteristics.id,
      height: bodyCharacteristics.height,
      weight: bodyCharacteristics.weight,
      date: bodyCharacteristics.date ? bodyCharacteristics.date.format(DATE_TIME_FORMAT) : null,
      heightUnitId: bodyCharacteristics.heightUnitId,
      widthUnitId: bodyCharacteristics.widthUnitId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bodyCharacteristics = this.createFromForm();
    if (bodyCharacteristics.id !== undefined) {
      this.subscribeToSaveResponse(this.bodyCharacteristicsService.update(bodyCharacteristics));
    } else {
      this.subscribeToSaveResponse(this.bodyCharacteristicsService.create(bodyCharacteristics));
    }
  }

  private createFromForm(): IBodyCharacteristics {
    return {
      ...new BodyCharacteristics(),
      id: this.editForm.get(['id'])!.value,
      height: this.editForm.get(['height'])!.value,
      weight: this.editForm.get(['weight'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      heightUnitId: this.editForm.get(['heightUnitId'])!.value,
      widthUnitId: this.editForm.get(['widthUnitId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBodyCharacteristics>>): void {
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

  trackById(index: number, item: IUnit): any {
    return item.id;
  }
}
