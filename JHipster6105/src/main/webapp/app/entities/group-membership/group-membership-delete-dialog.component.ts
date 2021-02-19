import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGroupMembership } from 'app/shared/model/group-membership.model';
import { GroupMembershipService } from './group-membership.service';

@Component({
  templateUrl: './group-membership-delete-dialog.component.html',
})
export class GroupMembershipDeleteDialogComponent {
  groupMembership?: IGroupMembership;

  constructor(
    protected groupMembershipService: GroupMembershipService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.groupMembershipService.delete(id).subscribe(() => {
      this.eventManager.broadcast('groupMembershipListModification');
      this.activeModal.close();
    });
  }
}
