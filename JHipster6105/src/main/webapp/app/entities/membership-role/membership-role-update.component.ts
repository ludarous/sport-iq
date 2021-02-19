import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMembershipRole, MembershipRole } from 'app/shared/model/membership-role.model';
import { MembershipRoleService } from './membership-role.service';

@Component({
  selector: 'jhi-membership-role-update',
  templateUrl: './membership-role-update.component.html',
})
export class MembershipRoleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
  });

  constructor(protected membershipRoleService: MembershipRoleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ membershipRole }) => {
      this.updateForm(membershipRole);
    });
  }

  updateForm(membershipRole: IMembershipRole): void {
    this.editForm.patchValue({
      id: membershipRole.id,
      name: membershipRole.name,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const membershipRole = this.createFromForm();
    if (membershipRole.id !== undefined) {
      this.subscribeToSaveResponse(this.membershipRoleService.update(membershipRole));
    } else {
      this.subscribeToSaveResponse(this.membershipRoleService.create(membershipRole));
    }
  }

  private createFromForm(): IMembershipRole {
    return {
      ...new MembershipRole(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMembershipRole>>): void {
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
