import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEvent, Event } from 'app/shared/model/event.model';
import { EventService } from './event.service';
import { IEventLocation } from 'app/shared/model/event-location.model';
import { EventLocationService } from 'app/entities/event-location/event-location.service';
import { IWorkout } from 'app/shared/model/workout.model';
import { WorkoutService } from 'app/entities/workout/workout.service';
import { IAthlete } from 'app/shared/model/athlete.model';
import { AthleteService } from 'app/entities/athlete/athlete.service';

type SelectableEntity = IEventLocation | IWorkout | IAthlete;

type SelectableManyToManyEntity = IWorkout | IAthlete;

@Component({
  selector: 'jhi-event-update',
  templateUrl: './event-update.component.html'
})
export class EventUpdateComponent implements OnInit {
  isSaving = false;
  eventlocations: IEventLocation[] = [];
  workouts: IWorkout[] = [];
  athletes: IAthlete[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    date: [],
    eventLocationId: [],
    tests: [],
    athletes: []
  });

  constructor(
    protected eventService: EventService,
    protected eventLocationService: EventLocationService,
    protected workoutService: WorkoutService,
    protected athleteService: AthleteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ event }) => {
      if (!event.id) {
        const today = moment().startOf('day');
        event.date = today;
      }

      this.updateForm(event);

      this.eventLocationService.query().subscribe((res: HttpResponse<IEventLocation[]>) => (this.eventlocations = res.body || []));

      this.workoutService.query().subscribe((res: HttpResponse<IWorkout[]>) => (this.workouts = res.body || []));

      this.athleteService.query().subscribe((res: HttpResponse<IAthlete[]>) => (this.athletes = res.body || []));
    });
  }

  updateForm(event: IEvent): void {
    this.editForm.patchValue({
      id: event.id,
      name: event.name,
      date: event.date ? event.date.format(DATE_TIME_FORMAT) : null,
      eventLocationId: event.eventLocationId,
      tests: event.tests,
      athletes: event.athletes
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const event = this.createFromForm();
    if (event.id !== undefined) {
      this.subscribeToSaveResponse(this.eventService.update(event));
    } else {
      this.subscribeToSaveResponse(this.eventService.create(event));
    }
  }

  private createFromForm(): IEvent {
    return {
      ...new Event(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      eventLocationId: this.editForm.get(['eventLocationId'])!.value,
      tests: this.editForm.get(['tests'])!.value,
      athletes: this.editForm.get(['athletes'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvent>>): void {
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
