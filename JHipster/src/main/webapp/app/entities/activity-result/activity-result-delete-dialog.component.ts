import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActivityResult } from 'app/shared/model/activity-result.model';
import { ActivityResultService } from './activity-result.service';

@Component({
  templateUrl: './activity-result-delete-dialog.component.html'
})
export class ActivityResultDeleteDialogComponent {
  activityResult: IActivityResult;

  constructor(
    protected activityResultService: ActivityResultService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.activityResultService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'activityResultListModification',
        content: 'Deleted an activityResult'
      });
      this.activeModal.dismiss(true);
    });
  }
}
