import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILegalRepresentative, LegalRepresentative } from 'app/shared/model/legal-representative.model';
import { LegalRepresentativeService } from './legal-representative.service';

@Component({
  selector: 'jhi-legal-representative-update',
  templateUrl: './legal-representative-update.component.html',
})
export class LegalRepresentativeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    firstName: [],
    lastName: [],
    email: [],
    phone: [],
  });

  constructor(
    protected legalRepresentativeService: LegalRepresentativeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ legalRepresentative }) => {
      this.updateForm(legalRepresentative);
    });
  }

  updateForm(legalRepresentative: ILegalRepresentative): void {
    this.editForm.patchValue({
      id: legalRepresentative.id,
      firstName: legalRepresentative.firstName,
      lastName: legalRepresentative.lastName,
      email: legalRepresentative.email,
      phone: legalRepresentative.phone,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const legalRepresentative = this.createFromForm();
    if (legalRepresentative.id !== undefined) {
      this.subscribeToSaveResponse(this.legalRepresentativeService.update(legalRepresentative));
    } else {
      this.subscribeToSaveResponse(this.legalRepresentativeService.create(legalRepresentative));
    }
  }

  private createFromForm(): ILegalRepresentative {
    return {
      ...new LegalRepresentative(),
      id: this.editForm.get(['id'])!.value,
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      email: this.editForm.get(['email'])!.value,
      phone: this.editForm.get(['phone'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILegalRepresentative>>): void {
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
