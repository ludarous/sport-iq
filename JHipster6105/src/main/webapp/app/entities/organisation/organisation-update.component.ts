import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOrganisation, Organisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from './organisation.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-organisation-update',
  templateUrl: './organisation-update.component.html',
})
export class OrganisationUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    created: [],
    description: [],
    owner: [],
  });

  constructor(
    protected organisationService: OrganisationService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organisation }) => {
      if (!organisation.id) {
        const today = moment().startOf('day');
        organisation.created = today;
      }

      this.updateForm(organisation);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(organisation: IOrganisation): void {
    this.editForm.patchValue({
      id: organisation.id,
      name: organisation.name,
      created: organisation.created ? organisation.created.format(DATE_TIME_FORMAT) : null,
      description: organisation.description,
      owner: organisation.owner,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const organisation = this.createFromForm();
    if (organisation.id !== undefined) {
      this.subscribeToSaveResponse(this.organisationService.update(organisation));
    } else {
      this.subscribeToSaveResponse(this.organisationService.create(organisation));
    }
  }

  private createFromForm(): IOrganisation {
    return {
      ...new Organisation(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      created: this.editForm.get(['created'])!.value ? moment(this.editForm.get(['created'])!.value, DATE_TIME_FORMAT) : undefined,
      description: this.editForm.get(['description'])!.value,
      owner: this.editForm.get(['owner'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrganisation>>): void {
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
