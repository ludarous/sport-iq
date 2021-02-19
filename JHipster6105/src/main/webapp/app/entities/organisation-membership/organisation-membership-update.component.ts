import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOrganisationMembership, OrganisationMembership } from 'app/shared/model/organisation-membership.model';
import { OrganisationMembershipService } from './organisation-membership.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IMembershipRole } from 'app/shared/model/membership-role.model';
import { MembershipRoleService } from 'app/entities/membership-role/membership-role.service';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';

type SelectableEntity = IUser | IMembershipRole | IOrganisation;

@Component({
  selector: 'jhi-organisation-membership-update',
  templateUrl: './organisation-membership-update.component.html',
})
export class OrganisationMembershipUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  membershiproles: IMembershipRole[] = [];
  organisations: IOrganisation[] = [];

  editForm = this.fb.group({
    id: [],
    created: [],
    user: [],
    roles: [],
    organisation: [],
  });

  constructor(
    protected organisationMembershipService: OrganisationMembershipService,
    protected userService: UserService,
    protected membershipRoleService: MembershipRoleService,
    protected organisationService: OrganisationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organisationMembership }) => {
      if (!organisationMembership.id) {
        const today = moment().startOf('day');
        organisationMembership.created = today;
      }

      this.updateForm(organisationMembership);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.membershipRoleService.query().subscribe((res: HttpResponse<IMembershipRole[]>) => (this.membershiproles = res.body || []));

      this.organisationService.query().subscribe((res: HttpResponse<IOrganisation[]>) => (this.organisations = res.body || []));
    });
  }

  updateForm(organisationMembership: IOrganisationMembership): void {
    this.editForm.patchValue({
      id: organisationMembership.id,
      created: organisationMembership.created ? organisationMembership.created.format(DATE_TIME_FORMAT) : null,
      user: organisationMembership.user,
      roles: organisationMembership.roles,
      organisation: organisationMembership.organisation,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organisationMembership = this.createFromForm();
    if (organisationMembership.id !== undefined) {
      this.subscribeToSaveResponse(this.organisationMembershipService.update(organisationMembership));
    } else {
      this.subscribeToSaveResponse(this.organisationMembershipService.create(organisationMembership));
    }
  }

  private createFromForm(): IOrganisationMembership {
    return {
      ...new OrganisationMembership(),
      id: this.editForm.get(['id'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      user: this.editForm.get(['user'])!.value,
      roles: this.editForm.get(['roles'])!.value,
      organisation: this.editForm.get(['organisation'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganisationMembership>>): void {
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

  getSelected(selectedVals: IMembershipRole[], option: IMembershipRole): IMembershipRole {
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
