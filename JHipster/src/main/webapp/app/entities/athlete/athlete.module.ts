import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared/shared.module';
import { AthleteComponent } from './athlete.component';
import { AthleteDetailComponent } from './athlete-detail.component';
import { AthleteUpdateComponent } from './athlete-update.component';
import { AthleteDeleteDialogComponent } from './athlete-delete-dialog.component';
import { athleteRoute } from './athlete.route';

@NgModule({
  imports: [SportiqSharedModule, RouterModule.forChild(athleteRoute)],
  declarations: [AthleteComponent, AthleteDetailComponent, AthleteUpdateComponent, AthleteDeleteDialogComponent],
  entryComponents: [AthleteDeleteDialogComponent]
})
export class SportiqAthleteModule {}
