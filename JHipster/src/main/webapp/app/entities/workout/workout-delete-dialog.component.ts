import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkout } from 'app/shared/model/workout.model';
import { WorkoutService } from './workout.service';

@Component({
  templateUrl: './workout-delete-dialog.component.html'
})
export class WorkoutDeleteDialogComponent {
  workout: IWorkout;

  constructor(protected workoutService: WorkoutService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.workoutService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'workoutListModification',
        content: 'Deleted an workout'
      });
      this.activeModal.dismiss(true);
    });
  }
}
