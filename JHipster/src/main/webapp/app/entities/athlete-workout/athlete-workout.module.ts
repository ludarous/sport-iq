import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared/shared.module';
import { AthleteWorkoutComponent } from './athlete-workout.component';
import { AthleteWorkoutDetailComponent } from './athlete-workout-detail.component';
import { AthleteWorkoutUpdateComponent } from './athlete-workout-update.component';
import { AthleteWorkoutDeleteDialogComponent } from './athlete-workout-delete-dialog.component';
import { athleteWorkoutRoute } from './athlete-workout.route';

@NgModule({
  imports: [SportiqSharedModule, RouterModule.forChild(athleteWorkoutRoute)],
  declarations: [
    AthleteWorkoutComponent,
    AthleteWorkoutDetailComponent,
    AthleteWorkoutUpdateComponent,
    AthleteWorkoutDeleteDialogComponent
  ],
  entryComponents: [AthleteWorkoutDeleteDialogComponent]
})
export class SportiqAthleteWorkoutModule {}
