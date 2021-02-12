import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserActivity } from 'app/shared/model/user-activity.model';
import { UserActivityService } from './user-activity.service';

@Component({
  templateUrl: './user-activity-delete-dialog.component.html',
})
export class UserActivityDeleteDialogComponent {
  userActivity?: IUserActivity;

  constructor(
    protected userActivityService: UserActivityService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userActivityService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userActivityListModification');
      this.activeModal.close();
    });
  }
}
