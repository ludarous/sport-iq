import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEvent } from 'app/shared/model/event.model';
import { EventService } from './event.service';

@Component({
  templateUrl: './event-delete-dialog.component.html'
})
export class EventDeleteDialogComponent {
  event: IEvent;

  constructor(protected eventService: EventService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.eventService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'eventListModification',
        content: 'Deleted an event'
      });
      this.activeModal.dismiss(true);
    });
  }
}
