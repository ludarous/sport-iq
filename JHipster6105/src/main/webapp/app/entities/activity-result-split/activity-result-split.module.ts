import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipster6105SharedModule } from 'app/shared/shared.module';
import { ActivityResultSplitComponent } from './activity-result-split.component';
import { ActivityResultSplitDetailComponent } from './activity-result-split-detail.component';
import { ActivityResultSplitUpdateComponent } from './activity-result-split-update.component';
import { ActivityResultSplitDeleteDialogComponent } from './activity-result-split-delete-dialog.component';
import { activityResultSplitRoute } from './activity-result-split.route';

@NgModule({
  imports: [JHipster6105SharedModule, RouterModule.forChild(activityResultSplitRoute)],
  declarations: [
    ActivityResultSplitComponent,
    ActivityResultSplitDetailComponent,
    ActivityResultSplitUpdateComponent,
    ActivityResultSplitDeleteDialogComponent,
  ],
  entryComponents: [ActivityResultSplitDeleteDialogComponent],
})
export class JHipster6105ActivityResultSplitModule {}
