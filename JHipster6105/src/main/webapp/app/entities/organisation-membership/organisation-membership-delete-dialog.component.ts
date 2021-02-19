import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOrganisationMembership } from 'app/shared/model/organisation-membership.model';
import { OrganisationMembershipService } from './organisation-membership.service';

@Component({
  templateUrl: './organisation-membership-delete-dialog.component.html',
})
export class OrganisationMembershipDeleteDialogComponent {
  organisationMembership?: IOrganisationMembership;

  constructor(
    protected organisationMembershipService: OrganisationMembershipService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.organisationMembershipService.delete(id).subscribe(() => {
      this.eventManager.broadcast('organisationMembershipListModification');
      this.activeModal.close();
    });
  }
}
