import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAthleteActivity } from 'app/shared/model/athlete-activity.model';
import { AthleteActivityService } from './athlete-activity.service';

@Component({
  templateUrl: './athlete-activity-delete-dialog.component.html'
})
export class AthleteActivityDeleteDialogComponent {
  athleteActivity?: IAthleteActivity;

  constructor(
    protected athleteActivityService: AthleteActivityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.athleteActivityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('athleteActivityListModification');
      this.activeModal.close();
    });
  }
}
