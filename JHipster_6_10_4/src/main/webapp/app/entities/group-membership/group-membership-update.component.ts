import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IGroupMembership, GroupMembership } from 'app/shared/model/group-membership.model';
import { GroupMembershipService } from './group-membership.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IMembershipRole } from 'app/shared/model/membership-role.model';
import { MembershipRoleService } from 'app/entities/membership-role/membership-role.service';
import { IGroup } from 'app/shared/model/group.model';
import { GroupService } from 'app/entities/group/group.service';

type SelectableEntity = IUser | IMembershipRole | IGroup;

@Component({
  selector: 'jhi-group-membership-update',
  templateUrl: './group-membership-update.component.html',
})
export class GroupMembershipUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  membershiproles: IMembershipRole[] = [];
  groups: IGroup[] = [];

  editForm = this.fb.group({
    id: [],
    created: [],
    userId: [],
    roles: [],
    groupId: [],
  });

  constructor(
    protected groupMembershipService: GroupMembershipService,
    protected userService: UserService,
    protected membershipRoleService: MembershipRoleService,
    protected groupService: GroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ groupMembership }) => {
      if (!groupMembership.id) {
        const today = moment().startOf('day');
        groupMembership.created = today;
      }

      this.updateForm(groupMembership);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.membershipRoleService.query().subscribe((res: HttpResponse<IMembershipRole[]>) => (this.membershiproles = res.body || []));

      this.groupService.query().subscribe((res: HttpResponse<IGroup[]>) => (this.groups = res.body || []));
    });
  }

  updateForm(groupMembership: IGroupMembership): void {
    this.editForm.patchValue({
      id: groupMembership.id,
      created: groupMembership.created ? groupMembership.created.format(DATE_TIME_FORMAT) : null,
      userId: groupMembership.userId,
      roles: groupMembership.roles,
      groupId: groupMembership.groupId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const groupMembership = this.createFromForm();
    if (groupMembership.id !== undefined) {
      this.subscribeToSaveResponse(this.groupMembershipService.update(groupMembership));
    } else {
      this.subscribeToSaveResponse(this.groupMembershipService.create(groupMembership));
    }
  }

  private createFromForm(): IGroupMembership {
    return {
      ...new GroupMembership(),
      id: this.editForm.get(['id'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      userId: this.editForm.get(['userId'])!.value,
      roles: this.editForm.get(['roles'])!.value,
      groupId: this.editForm.get(['groupId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGroupMembership>>): void {
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
