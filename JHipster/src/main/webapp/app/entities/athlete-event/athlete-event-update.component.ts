import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IAthleteEvent, AthleteEvent } from 'app/shared/model/athlete-event.model';
import { AthleteEventService } from './athlete-event.service';
import { IAthlete } from 'app/shared/model/athlete.model';
import { AthleteService } from 'app/entities/athlete/athlete.service';
import { IEvent } from 'app/shared/model/event.model';
import { EventService } from 'app/entities/event/event.service';

@Component({
  selector: 'jhi-athlete-event-update',
  templateUrl: './athlete-event-update.component.html'
})
export class AthleteEventUpdateComponent implements OnInit {
  isSaving: boolean;

  athletes: IAthlete[];

  events: IEvent[];

  editForm = this.fb.group({
    id: [],
    note: [],
    actualHeightInCm: [],
    actualWeightInKg: [],
    athleteId: [null, Validators.required],
    eventId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected athleteEventService: AthleteEventService,
    protected athleteService: AthleteService,
    protected eventService: EventService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ athleteEvent }) => {
      this.updateForm(athleteEvent);
    });
    this.athleteService
      .query()
      .subscribe((res: HttpResponse<IAthlete[]>) => (this.athletes = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.eventService
      .query()
      .subscribe((res: HttpResponse<IEvent[]>) => (this.events = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(athleteEvent: IAthleteEvent) {
    this.editForm.patchValue({
      id: athleteEvent.id,
      note: athleteEvent.note,
      actualHeightInCm: athleteEvent.actualHeightInCm,
      actualWeightInKg: athleteEvent.actualWeightInKg,
      athleteId: athleteEvent.athleteId,
      eventId: athleteEvent.eventId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
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
      id: this.editForm.get(['id']).value,
      note: this.editForm.get(['note']).value,
      actualHeightInCm: this.editForm.get(['actualHeightInCm']).value,
      actualWeightInKg: this.editForm.get(['actualWeightInKg']).value,
      athleteId: this.editForm.get(['athleteId']).value,
      eventId: this.editForm.get(['eventId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAthleteEvent>>) {
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

  trackAthleteById(index: number, item: IAthlete) {
    return item.id;
  }

  trackEventById(index: number, item: IEvent) {
    return item.id;
  }
}
