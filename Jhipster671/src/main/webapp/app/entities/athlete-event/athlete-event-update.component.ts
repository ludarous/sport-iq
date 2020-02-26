import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAthleteEvent, AthleteEvent } from 'app/shared/model/athlete-event.model';
import { AthleteEventService } from './athlete-event.service';
import { IAthlete } from 'app/shared/model/athlete.model';
import { AthleteService } from 'app/entities/athlete/athlete.service';
import { IEvent } from 'app/shared/model/event.model';
import { EventService } from 'app/entities/event/event.service';

type SelectableEntity = IAthlete | IEvent;

@Component({
  selector: 'jhi-athlete-event-update',
  templateUrl: './athlete-event-update.component.html'
})
export class AthleteEventUpdateComponent implements OnInit {
  isSaving = false;
  athletes: IAthlete[] = [];
  events: IEvent[] = [];

  editForm = this.fb.group({
    id: [],
    note: [],
    actualHeightInCm: [],
    actualWeightInKg: [],
    athleteId: [null, Validators.required],
    eventId: []
  });

  constructor(
    protected athleteEventService: AthleteEventService,
    protected athleteService: AthleteService,
    protected eventService: EventService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ athleteEvent }) => {
      this.updateForm(athleteEvent);

      this.athleteService.query().subscribe((res: HttpResponse<IAthlete[]>) => (this.athletes = res.body || []));

      this.eventService.query().subscribe((res: HttpResponse<IEvent[]>) => (this.events = res.body || []));
    });
  }

  updateForm(athleteEvent: IAthleteEvent): void {
    this.editForm.patchValue({
      id: athleteEvent.id,
      note: athleteEvent.note,
      actualHeightInCm: athleteEvent.actualHeightInCm,
      actualWeightInKg: athleteEvent.actualWeightInKg,
      athleteId: athleteEvent.athleteId,
      eventId: athleteEvent.eventId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const athleteEvent = this.createFromForm();
    if (athleteEvent.id !== undefined) {
      this.subscribeToSaveResponse(this.athleteEventService.update(athleteEvent));
    } else {
      this.subscribeToSaveResponse(this.athleteEventService.create(athleteEvent));
    }
  }

  private createFromForm(): IAthleteEvent {
    return {
      ...new AthleteEvent(),
      id: this.editForm.get(['id'])!.value,
      note: this.editForm.get(['note'])!.value,
      actualHeightInCm: this.editForm.get(['actualHeightInCm'])!.value,
      actualWeightInKg: this.editForm.get(['actualWeightInKg'])!.value,
      athleteId: this.editForm.get(['athleteId'])!.value,
      eventId: this.editForm.get(['eventId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAthleteEvent>>): void {
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
