import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAthleteWorkout } from 'app/shared/model/athlete-workout.model';
import { AthleteWorkoutService } from './athlete-workout.service';

@Component({
  templateUrl: './athlete-workout-delete-dialog.component.html'
})
export class AthleteWorkoutDeleteDialogComponent {
  athleteWorkout?: IAthleteWorkout;

  constructor(
    protected athleteWorkoutService: AthleteWorkoutService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.athleteWorkoutService.delete(id).subscribe(() => {
      this.eventManager.broadcast('athleteWorkoutListModification');
      this.activeModal.close();
    });
  }
}
