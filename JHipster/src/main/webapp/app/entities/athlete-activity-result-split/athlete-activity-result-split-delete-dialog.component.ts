import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAthleteActivityResultSplit } from 'app/shared/model/athlete-activity-result-split.model';
import { AthleteActivityResultSplitService } from './athlete-activity-result-split.service';

@Component({
  templateUrl: './athlete-activity-result-split-delete-dialog.component.html'
})
export class AthleteActivityResultSplitDeleteDialogComponent {
  athleteActivityResultSplit: IAthleteActivityResultSplit;

  constructor(
    protected athleteActivityResultSplitService: AthleteActivityResultSplitService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.athleteActivityResultSplitService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'athleteActivityResultSplitListModification',
        content: 'Deleted an athleteActivityResultSplit'
      });
      this.activeModal.dismiss(true);
    });
  }
}
