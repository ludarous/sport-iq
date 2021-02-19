import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserProperties } from 'app/shared/model/user-properties.model';
import { UserPropertiesService } from './user-properties.service';

@Component({
  templateUrl: './user-properties-delete-dialog.component.html',
})
export class UserPropertiesDeleteDialogComponent {
  userProperties?: IUserProperties;

  constructor(
    protected userPropertiesService: UserPropertiesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userPropertiesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userPropertiesListModification');
      this.activeModal.close();
    });
  }
}
