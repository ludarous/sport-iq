import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared/shared.module';
import { AthleteActivityResultSplitComponent } from './athlete-activity-result-split.component';
import { AthleteActivityResultSplitDetailComponent } from './athlete-activity-result-split-detail.component';
import { AthleteActivityResultSplitUpdateComponent } from './athlete-activity-result-split-update.component';
import { AthleteActivityResultSplitDeleteDialogComponent } from './athlete-activity-result-split-delete-dialog.component';
import { athleteActivityResultSplitRoute } from './athlete-activity-result-split.route';

@NgModule({
  imports: [SportiqSharedModule, RouterModule.forChild(athleteActivityResultSplitRoute)],
  declarations: [
    AthleteActivityResultSplitComponent,
    AthleteActivityResultSplitDetailComponent,
    AthleteActivityResultSplitUpdateComponent,
    AthleteActivityResultSplitDeleteDialogComponent
  ],
  entryComponents: [AthleteActivityResultSplitDeleteDialogComponent]
})
export class SportiqAthleteActivityResultSplitModule {}
