import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAthlete } from 'app/shared/model/athlete.model';
import { AthleteService } from './athlete.service';

@Component({
  templateUrl: './athlete-delete-dialog.component.html'
})
export class AthleteDeleteDialogComponent {
  athlete?: IAthlete;

  constructor(protected athleteService: AthleteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.athleteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('athleteListModification');
      this.activeModal.close();
    });
  }
}
