import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActivityResult } from 'app/shared/model/activity-result.model';
import { ActivityResultService } from './activity-result.service';

@Component({
  templateUrl: './activity-result-delete-dialog.component.html',
})
export class ActivityResultDeleteDialogComponent {
  activityResult?: IActivityResult;

  constructor(
    protected activityResultService: ActivityResultService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.activityResultService.delete(id).subscribe(() => {
      this.eventManager.broadcast('activityResultListModification');
      this.activeModal.close();
    });
  }
}
