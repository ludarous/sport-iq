import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISport } from 'app/shared/model/sport.model';
import { SportService } from './sport.service';

@Component({
  templateUrl: './sport-delete-dialog.component.html'
})
export class SportDeleteDialogComponent {
  sport: ISport;

  constructor(protected sportService: SportService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.sportService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'sportListModification',
        content: 'Deleted an sport'
      });
      this.activeModal.dismiss(true);
    });
  }
}
