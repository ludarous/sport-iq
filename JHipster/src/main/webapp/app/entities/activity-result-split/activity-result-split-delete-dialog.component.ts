import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActivityResultSplit } from 'app/shared/model/activity-result-split.model';
import { ActivityResultSplitService } from './activity-result-split.service';

@Component({
  templateUrl: './activity-result-split-delete-dialog.component.html'
})
export class ActivityResultSplitDeleteDialogComponent {
  activityResultSplit: IActivityResultSplit;

  constructor(
    protected activityResultSplitService: ActivityResultSplitService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.activityResultSplitService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'activityResultSplitListModification',
        content: 'Deleted an activityResultSplit'
      });
      this.activeModal.dismiss(true);
    });
  }
}
