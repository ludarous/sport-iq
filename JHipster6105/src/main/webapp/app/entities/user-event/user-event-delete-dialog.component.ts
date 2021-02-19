import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserEvent } from 'app/shared/model/user-event.model';
import { UserEventService } from './user-event.service';

@Component({
  templateUrl: './user-event-delete-dialog.component.html',
})
export class UserEventDeleteDialogComponent {
  userEvent?: IUserEvent;

  constructor(protected userEventService: UserEventService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userEventService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userEventListModification');
      this.activeModal.close();
    });
  }
}
