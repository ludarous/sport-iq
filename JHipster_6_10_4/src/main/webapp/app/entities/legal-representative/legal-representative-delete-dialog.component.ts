import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILegalRepresentative } from 'app/shared/model/legal-representative.model';
import { LegalRepresentativeService } from './legal-representative.service';

@Component({
  templateUrl: './legal-representative-delete-dialog.component.html',
})
export class LegalRepresentativeDeleteDialogComponent {
  legalRepresentative?: ILegalRepresentative;

  constructor(
    protected legalRepresentativeService: LegalRepresentativeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.legalRepresentativeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('legalRepresentativeListModification');
      this.activeModal.close();
    });
  }
}
