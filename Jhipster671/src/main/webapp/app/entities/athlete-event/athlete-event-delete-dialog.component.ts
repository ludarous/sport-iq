import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAthleteEvent } from 'app/shared/model/athlete-event.model';
import { AthleteEventService } from './athlete-event.service';

@Component({
  templateUrl: './athlete-event-delete-dialog.component.html'
})
export class AthleteEventDeleteDialogComponent {
  athleteEvent?: IAthleteEvent;

  constructor(
    protected athleteEventService: AthleteEventService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.athleteEventService.delete(id).subscribe(() => {
      this.eventManager.broadcast('athleteEventListModification');
      this.activeModal.close();
    });
  }
}
