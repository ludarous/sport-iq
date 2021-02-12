import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportIqSharedModule } from 'app/shared/shared.module';
import { UserActivityResultSplitComponent } from './user-activity-result-split.component';
import { UserActivityResultSplitDetailComponent } from './user-activity-result-split-detail.component';
import { UserActivityResultSplitUpdateComponent } from './user-activity-result-split-update.component';
import { UserActivityResultSplitDeleteDialogComponent } from './user-activity-result-split-delete-dialog.component';
import { userActivityResultSplitRoute } from './user-activity-result-split.route';

@NgModule({
  imports: [SportIqSharedModule, RouterModule.forChild(userActivityResultSplitRoute)],
  declarations: [
    UserActivityResultSplitComponent,
    UserActivityResultSplitDetailComponent,
    UserActivityResultSplitUpdateComponent,
    UserActivityResultSplitDeleteDialogComponent,
  ],
  entryComponents: [UserActivityResultSplitDeleteDialogComponent],
})
export class SportIqUserActivityResultSplitModule {}
