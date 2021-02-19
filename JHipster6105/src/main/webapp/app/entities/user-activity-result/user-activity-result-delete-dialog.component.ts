import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserActivityResult } from 'app/shared/model/user-activity-result.model';
import { UserActivityResultService } from './user-activity-result.service';

@Component({
  templateUrl: './user-activity-result-delete-dialog.component.html',
})
export class UserActivityResultDeleteDialogComponent {
  userActivityResult?: IUserActivityResult;

  constructor(
    protected userActivityResultService: UserActivityResultService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userActivityResultService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userActivityResultListModification');
      this.activeModal.close();
    });
  }
}
