import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IActivity, Activity } from 'app/shared/model/activity.model';
import { ActivityService } from './activity.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from 'app/entities/organisation/organisation.service';
import { IGroup } from 'app/shared/model/group.model';
import { GroupService } from 'app/entities/group/group.service';

type SelectableEntity = IUser | IOrganisation | IGroup;

type SelectableManyToManyEntity = IOrganisation | IGroup;

@Component({
  selector: 'jhi-activity-update',
  templateUrl: './activity-update.component.html',
})
export class ActivityUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  organisations: IOrganisation[] = [];
  groups: IGroup[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    isPublic: [],
    description: [],
    help: [],
    createdBy: [],
    visibleForOrganisations: [],
    visibleForGroups: [],
  });

  constructor(
    protected activityService: ActivityService,
    protected userService: UserService,
    protected organisationService: OrganisationService,
    protected groupService: GroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activity }) => {
      this.updateForm(activity);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.organisationService.query().subscribe((res: HttpResponse<IOrganisation[]>) => (this.organisations = res.body || []));

      this.groupService.query().subscribe((res: HttpResponse<IGroup[]>) => (this.groups = res.body || []));
    });
  }

  updateForm(activity: IActivity): void {
    this.editForm.patchValue({
      id: activity.id,
      name: activity.name,
      isPublic: activity.isPublic,
      description: activity.description,
      help: activity.help,
      createdBy: activity.createdBy,
      visibleForOrganisations: activity.visibleForOrganisations,
      visibleForGroups: activity.visibleForGroups,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const activity = this.createFromForm();
    if (activity.id !== undefined) {
      this.subscribeToSaveResponse(this.activityService.update(activity));
    } else {
      this.subscribeToSaveResponse(this.activityService.create(activity));
    }
  }

  private createFromForm(): IActivity {
    return {
      ...new Activity(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      isPublic: this.editForm.get(['isPublic'])!.value,
      description: this.editForm.get(['description'])!.value,
      help: this.editForm.get(['help'])!.value,
      createdBy: this.editForm.get(['createdBy'])!.value,
      visibleForOrganisations: this.editForm.get(['visibleForOrganisations'])!.value,
      visibleForGroups: this.editForm.get(['visibleForGroups'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivity>>): void {
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

  getSelected(selectedVals: SelectableManyToManyEntity[], option: SelectableManyToManyEntity): SelectableManyToManyEntity {
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
