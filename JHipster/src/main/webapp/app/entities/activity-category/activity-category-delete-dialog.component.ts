import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActivityCategory } from 'app/shared/model/activity-category.model';
import { ActivityCategoryService } from './activity-category.service';

@Component({
  templateUrl: './activity-category-delete-dialog.component.html'
})
export class ActivityCategoryDeleteDialogComponent {
  activityCategory: IActivityCategory;

  constructor(
    protected activityCategoryService: ActivityCategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.activityCategoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'activityCategoryListModification',
        content: 'Deleted an activityCategory'
      });
      this.activeModal.dismiss(true);
    });
  }
}
