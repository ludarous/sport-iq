import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUserEvent, UserEvent } from 'app/shared/model/user-event.model';
import { UserEventService } from './user-event.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IEvent } from 'app/shared/model/event.model';
import { EventService } from 'app/entities/event/event.service';

type SelectableEntity = IUser | IEvent;

@Component({
  selector: 'jhi-user-event-update',
  templateUrl: './user-event-update.component.html',
})
export class UserEventUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  events: IEvent[] = [];

  editForm = this.fb.group({
    id: [],
    note: [],
    registrationDate: [],
    user: [null, Validators.required],
    event: [null, Validators.required],
  });

  constructor(
    protected userEventService: UserEventService,
    protected userService: UserService,
    protected eventService: EventService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userEvent }) => {
      if (!userEvent.id) {
        const today = moment().startOf('day');
        userEvent.registrationDate = today;
      }

      this.updateForm(userEvent);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.eventService.query().subscribe((res: HttpResponse<IEvent[]>) => (this.events = res.body || []));
    });
  }

  updateForm(userEvent: IUserEvent): void {
    this.editForm.patchValue({
      id: userEvent.id,
      note: userEvent.note,
      registrationDate: userEvent.registrationDate ? userEvent.registrationDate.format(DATE_TIME_FORMAT) : null,
      user: userEvent.user,
      event: userEvent.event,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userEvent = this.createFromForm();
    if (userEvent.id !== undefined) {
      this.subscribeToSaveResponse(this.userEventService.update(userEvent));
    } else {
      this.subscribeToSaveResponse(this.userEventService.create(userEvent));
    }
  }

  private createFromForm(): IUserEvent {
    return {
      ...new UserEvent(),
      id: this.editForm.get(['id'])!.value,
      note: this.editForm.get(['note'])!.value,
      registrationDate: this.editForm.get(['registrationDate'])!.value
        ? moment(this.editForm.get(['registrationDate'])!.value, DATE_TIME_FORMAT)
        : undefined,
      user: this.editForm.get(['user'])!.value,
      event: this.editForm.get(['event'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserEvent>>): void {
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
}
