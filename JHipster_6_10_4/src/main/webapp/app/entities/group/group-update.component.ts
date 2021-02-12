import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IGroup, Group } from 'app/shared/model/group.model';
import { GroupService } from './group.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-group-update',
  templateUrl: './group-update.component.html',
})
export class GroupUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    created: [],
    description: [],
    ownerId: [],
  });

  constructor(
    protected groupService: GroupService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ group }) => {
      if (!group.id) {
        const today = moment().startOf('day');
        group.created = today;
      }

      this.updateForm(group);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(group: IGroup): void {
    this.editForm.patchValue({
      id: group.id,
      name: group.name,
      created: group.created ? group.created.format(DATE_TIME_FORMAT) : null,
      description: group.description,
      ownerId: group.ownerId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const group = this.createFromForm();
    if (group.id !== undefined) {
      this.subscribeToSaveResponse(this.groupService.update(group));
    } else {
      this.subscribeToSaveResponse(this.groupService.create(group));
    }
  }

  private createFromForm(): IGroup {
    return {
      ...new Group(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      description: this.editForm.get(['description'])!.value,
      ownerId: this.editForm.get(['ownerId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGroup>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
