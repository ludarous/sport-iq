import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEventLocation, EventLocation } from 'app/shared/model/event-location.model';
import { EventLocationService } from './event-location.service';

@Component({
  selector: 'jhi-event-location-update',
  templateUrl: './event-location-update.component.html'
})
export class EventLocationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    state: [],
    city: [],
    street: [],
    streetNumber: [],
    latitude: [],
    longitude: [],
    capacity: [],
    mapLink: [],
    maxAge: [],
    minAge: []
  });

  constructor(protected eventLocationService: EventLocationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventLocation }) => {
      this.updateForm(eventLocation);
    });
  }

  updateForm(eventLocation: IEventLocation): void {
    this.editForm.patchValue({
      id: eventLocation.id,
      name: eventLocation.name,
      state: eventLocation.state,
      city: eventLocation.city,
      street: eventLocation.street,
      streetNumber: eventLocation.streetNumber,
      latitude: eventLocation.latitude,
      longitude: eventLocation.longitude,
      capacity: eventLocation.capacity,
      mapLink: eventLocation.mapLink,
      maxAge: eventLocation.maxAge,
      minAge: eventLocation.minAge
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventLocation = this.createFromForm();
    if (eventLocation.id !== undefined) {
      this.subscribeToSaveResponse(this.eventLocationService.update(eventLocation));
    } else {
      this.subscribeToSaveResponse(this.eventLocationService.create(eventLocation));
    }
  }

  private createFromForm(): IEventLocation {
    return {
      ...new EventLocation(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      state: this.editForm.get(['state'])!.value,
      city: this.editForm.get(['city'])!.value,
      street: this.editForm.get(['street'])!.value,
      streetNumber: this.editForm.get(['streetNumber'])!.value,
      latitude: this.editForm.get(['latitude'])!.value,
      longitude: this.editForm.get(['longitude'])!.value,
      capacity: this.editForm.get(['capacity'])!.value,
      mapLink: this.editForm.get(['mapLink'])!.value,
      maxAge: this.editForm.get(['maxAge'])!.value,
      minAge: this.editForm.get(['minAge'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventLocation>>): void {
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
}
