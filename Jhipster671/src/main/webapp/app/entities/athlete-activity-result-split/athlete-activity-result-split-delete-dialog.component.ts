import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAthleteActivityResultSplit } from 'app/shared/model/athlete-activity-result-split.model';
import { AthleteActivityResultSplitService } from './athlete-activity-result-split.service';

@Component({
  templateUrl: './athlete-activity-result-split-delete-dialog.component.html'
})
export class AthleteActivityResultSplitDeleteDialogComponent {
  athleteActivityResultSplit?: IAthleteActivityResultSplit;

  constructor(
    protected athleteActivityResultSplitService: AthleteActivityResultSplitService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.athleteActivityResultSplitService.delete(id).subscribe(() => {
      this.eventManager.broadcast('athleteActivityResultSplitListModification');
      this.activeModal.close();
    });
  }
}
