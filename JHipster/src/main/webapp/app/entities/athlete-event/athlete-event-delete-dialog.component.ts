import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAthleteEvent } from 'app/shared/model/athlete-event.model';
import { AthleteEventService } from './athlete-event.service';

@Component({
  templateUrl: './athlete-event-delete-dialog.component.html'
})
export class AthleteEventDeleteDialogComponent {
  athleteEvent: IAthleteEvent;

  constructor(
    protected athleteEventService: AthleteEventService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.athleteEventService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'athleteEventListModification',
        content: 'Deleted an athleteEvent'
      });
      this.activeModal.dismiss(true);
    });
  }
}
