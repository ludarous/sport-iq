import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEventLocation } from 'app/shared/model/event-location.model';
import { EventLocationService } from './event-location.service';

@Component({
  templateUrl: './event-location-delete-dialog.component.html'
})
export class EventLocationDeleteDialogComponent {
  eventLocation: IEventLocation;

  constructor(
    protected eventLocationService: EventLocationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.eventLocationService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'eventLocationListModification',
        content: 'Deleted an eventLocation'
      });
      this.activeModal.dismiss(true);
    });
  }
}
