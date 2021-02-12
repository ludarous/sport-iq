import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBodyCharacteristics } from 'app/shared/model/body-characteristics.model';
import { BodyCharacteristicsService } from './body-characteristics.service';

@Component({
  templateUrl: './body-characteristics-delete-dialog.component.html',
})
export class BodyCharacteristicsDeleteDialogComponent {
  bodyCharacteristics?: IBodyCharacteristics;

  constructor(
    protected bodyCharacteristicsService: BodyCharacteristicsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bodyCharacteristicsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bodyCharacteristicsListModification');
      this.activeModal.close();
    });
  }
}
