import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAgreement, Agreement } from 'app/shared/model/agreement.model';
import { AgreementService } from './agreement.service';

@Component({
  selector: 'jhi-agreement-update',
  templateUrl: './agreement-update.component.html',
})
export class AgreementUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    termsAgreement: [],
    gdprAgreement: [],
    photographyAgreement: [],
    marketingAgreement: [],
    medicalFitnessAgreement: [],
  });

  constructor(protected agreementService: AgreementService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agreement }) => {
      this.updateForm(agreement);
    });
  }

  updateForm(agreement: IAgreement): void {
    this.editForm.patchValue({
      id: agreement.id,
      termsAgreement: agreement.termsAgreement,
      gdprAgreement: agreement.gdprAgreement,
      photographyAgreement: agreement.photographyAgreement,
      marketingAgreement: agreement.marketingAgreement,
      medicalFitnessAgreement: agreement.medicalFitnessAgreement,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agreement = this.createFromForm();
    if (agreement.id !== undefined) {
      this.subscribeToSaveResponse(this.agreementService.update(agreement));
    } else {
      this.subscribeToSaveResponse(this.agreementService.create(agreement));
    }
  }

  private createFromForm(): IAgreement {
    return {
      ...new Agreement(),
      id: this.editForm.get(['id'])!.value,
      termsAgreement: this.editForm.get(['termsAgreement'])!.value,
      gdprAgreement: this.editForm.get(['gdprAgreement'])!.value,
      photographyAgreement: this.editForm.get(['photographyAgreement'])!.value,
      marketingAgreement: this.editForm.get(['marketingAgreement'])!.value,
      medicalFitnessAgreement: this.editForm.get(['medicalFitnessAgreement'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgreement>>): void {
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
