import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserActivityResultSplit } from 'app/shared/model/user-activity-result-split.model';
import { UserActivityResultSplitService } from './user-activity-result-split.service';

@Component({
  templateUrl: './user-activity-result-split-delete-dialog.component.html',
})
export class UserActivityResultSplitDeleteDialogComponent {
  userActivityResultSplit?: IUserActivityResultSplit;

  constructor(
    protected userActivityResultSplitService: UserActivityResultSplitService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userActivityResultSplitService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userActivityResultSplitListModification');
      this.activeModal.close();
    });
  }
}
