import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
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
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    state: [],
    city: [],
    street: [],
    streetNumber: [],
    latitude: [],
    longitude: []
  });

  constructor(protected eventLocationService: EventLocationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ eventLocation }) => {
      this.updateForm(eventLocation);
    });
  }

  updateForm(eventLocation: IEventLocation) {
    this.editForm.patchValue({
      id: eventLocation.id,
      name: eventLocation.name,
      state: eventLocation.state,
      city: eventLocation.city,
      street: eventLocation.street,
      streetNumber: eventLocation.streetNumber,
      latitude: eventLocation.latitude,
      longitude: eventLocation.longitude
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
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
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      state: this.editForm.get(['state']).value,
      city: this.editForm.get(['city']).value,
      street: this.editForm.get(['street']).value,
      streetNumber: this.editForm.get(['streetNumber']).value,
      latitude: this.editForm.get(['latitude']).value,
      longitude: this.editForm.get(['longitude']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventLocation>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
