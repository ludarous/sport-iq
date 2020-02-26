import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkoutCategory } from 'app/shared/model/workout-category.model';
import { WorkoutCategoryService } from './workout-category.service';

@Component({
  templateUrl: './workout-category-delete-dialog.component.html'
})
export class WorkoutCategoryDeleteDialogComponent {
  workoutCategory?: IWorkoutCategory;

  constructor(
    protected workoutCategoryService: WorkoutCategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.workoutCategoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('workoutCategoryListModification');
      this.activeModal.close();
    });
  }
}
