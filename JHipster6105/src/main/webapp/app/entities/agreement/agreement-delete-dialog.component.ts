import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgreement } from 'app/shared/model/agreement.model';
import { AgreementService } from './agreement.service';

@Component({
  templateUrl: './agreement-delete-dialog.component.html',
})
export class AgreementDeleteDialogComponent {
  agreement?: IAgreement;

  constructor(protected agreementService: AgreementService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.agreementService.delete(id).subscribe(() => {
      this.eventManager.broadcast('agreementListModification');
      this.activeModal.close();
    });
  }
}
