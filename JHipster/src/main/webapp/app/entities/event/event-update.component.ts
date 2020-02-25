import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IEvent, Event } from 'app/shared/model/event.model';
import { EventService } from './event.service';
import { IEventLocation } from 'app/shared/model/event-location.model';
import { EventLocationService } from 'app/entities/event-location/event-location.service';
import { IWorkout } from 'app/shared/model/workout.model';
import { WorkoutService } from 'app/entities/workout/workout.service';
import { IAthlete } from 'app/shared/model/athlete.model';
import { AthleteService } from 'app/entities/athlete/athlete.service';

@Component({
  selector: 'jhi-event-update',
  templateUrl: './event-update.component.html'
})
export class EventUpdateComponent implements OnInit {
  isSaving: boolean;

  eventlocations: IEventLocation[];

  workouts: IWorkout[];

  athletes: IAthlete[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    date: [],
    eventLocationId: [],
    tests: [],
    athletes: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected eventService: EventService,
    protected eventLocationService: EventLocationService,
    protected workoutService: WorkoutService,
    protected athleteService: AthleteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ event }) => {
      this.updateForm(event);
    });
    this.eventLocationService
      .query()
      .subscribe(
        (res: HttpResponse<IEventLocation[]>) => (this.eventlocations = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.workoutService
      .query()
      .subscribe((res: HttpResponse<IWorkout[]>) => (this.workouts = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.athleteService
      .query()
      .subscribe((res: HttpResponse<IAthlete[]>) => (this.athletes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(event: IEvent) {
    this.editForm.patchValue({
      id: event.id,
      name: event.name,
      date: event.date != null ? event.date.format(DATE_TIME_FORMAT) : null,
      eventLocationId: event.eventLocationId,
      tests: event.tests,
      athletes: event.athletes
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
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
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      date: this.editForm.get(['date']).value != null ? moment(this.editForm.get(['date']).value, DATE_TIME_FORMAT) : undefined,
      eventLocationId: this.editForm.get(['eventLocationId']).value,
      tests: this.editForm.get(['tests']).value,
      athletes: this.editForm.get(['athletes']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvent>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackEventLocationById(index: number, item: IEventLocation) {
    return item.id;
  }

  trackWorkoutById(index: number, item: IWorkout) {
    return item.id;
  }

  trackAthleteById(index: number, item: IAthlete) {
    return item.id;
  }

  getSelected(selectedVals: any[], option: any) {
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
