import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAthleteActivityResult } from 'app/shared/model/athlete-activity-result.model';
import { AthleteActivityResultService } from './athlete-activity-result.service';

@Component({
  templateUrl: './athlete-activity-result-delete-dialog.component.html'
})
export class AthleteActivityResultDeleteDialogComponent {
  athleteActivityResult: IAthleteActivityResult;

  constructor(
    protected athleteActivityResultService: AthleteActivityResultService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.athleteActivityResultService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'athleteActivityResultListModification',
        content: 'Deleted an athleteActivityResult'
      });
      this.activeModal.dismiss(true);
    });
  }
}
