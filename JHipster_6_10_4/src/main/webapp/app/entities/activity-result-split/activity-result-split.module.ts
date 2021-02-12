import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportIqSharedModule } from 'app/shared/shared.module';
import { ActivityResultSplitComponent } from './activity-result-split.component';
import { ActivityResultSplitDetailComponent } from './activity-result-split-detail.component';
import { ActivityResultSplitUpdateComponent } from './activity-result-split-update.component';
import { ActivityResultSplitDeleteDialogComponent } from './activity-result-split-delete-dialog.component';
import { activityResultSplitRoute } from './activity-result-split.route';

@NgModule({
  imports: [SportIqSharedModule, RouterModule.forChild(activityResultSplitRoute)],
  declarations: [
    ActivityResultSplitComponent,
    ActivityResultSplitDetailComponent,
    ActivityResultSplitUpdateComponent,
    ActivityResultSplitDeleteDialogComponent,
  ],
  entryComponents: [ActivityResultSplitDeleteDialogComponent],
})
export class SportIqActivityResultSplitModule {}
