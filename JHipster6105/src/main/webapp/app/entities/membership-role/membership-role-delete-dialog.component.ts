import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMembershipRole } from 'app/shared/model/membership-role.model';
import { MembershipRoleService } from './membership-role.service';

@Component({
  templateUrl: './membership-role-delete-dialog.component.html',
})
export class MembershipRoleDeleteDialogComponent {
  membershipRole?: IMembershipRole;

  constructor(
    protected membershipRoleService: MembershipRoleService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.membershipRoleService.delete(id).subscribe(() => {
      this.eventManager.broadcast('membershipRoleListModification');
      this.activeModal.close();
    });
  }
}
