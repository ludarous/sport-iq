import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared/shared.module';
import { AthleteActivityResultComponent } from './athlete-activity-result.component';
import { AthleteActivityResultDetailComponent } from './athlete-activity-result-detail.component';
import { AthleteActivityResultUpdateComponent } from './athlete-activity-result-update.component';
import { AthleteActivityResultDeleteDialogComponent } from './athlete-activity-result-delete-dialog.component';
import { athleteActivityResultRoute } from './athlete-activity-result.route';

@NgModule({
  imports: [SportiqSharedModule, RouterModule.forChild(athleteActivityResultRoute)],
  declarations: [
    AthleteActivityResultComponent,
    AthleteActivityResultDetailComponent,
    AthleteActivityResultUpdateComponent,
    AthleteActivityResultDeleteDialogComponent
  ],
  entryComponents: [AthleteActivityResultDeleteDialogComponent]
})
export class SportiqAthleteActivityResultModule {}
