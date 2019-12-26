import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared/shared.module';
import { AthleteActivityComponent } from './athlete-activity.component';
import { AthleteActivityDetailComponent } from './athlete-activity-detail.component';
import { AthleteActivityUpdateComponent } from './athlete-activity-update.component';
import { AthleteActivityDeleteDialogComponent } from './athlete-activity-delete-dialog.component';
import { athleteActivityRoute } from './athlete-activity.route';

@NgModule({
  imports: [SportiqSharedModule, RouterModule.forChild(athleteActivityRoute)],
  declarations: [
    AthleteActivityComponent,
    AthleteActivityDetailComponent,
    AthleteActivityUpdateComponent,
    AthleteActivityDeleteDialogComponent
  ],
  entryComponents: [AthleteActivityDeleteDialogComponent]
})
export class SportiqAthleteActivityModule {}
