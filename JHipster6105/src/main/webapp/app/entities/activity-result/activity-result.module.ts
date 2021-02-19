import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipster6105SharedModule } from 'app/shared/shared.module';
import { ActivityResultComponent } from './activity-result.component';
import { ActivityResultDetailComponent } from './activity-result-detail.component';
import { ActivityResultUpdateComponent } from './activity-result-update.component';
import { ActivityResultDeleteDialogComponent } from './activity-result-delete-dialog.component';
import { activityResultRoute } from './activity-result.route';

@NgModule({
  imports: [JHipster6105SharedModule, RouterModule.forChild(activityResultRoute)],
  declarations: [
    ActivityResultComponent,
    ActivityResultDetailComponent,
    ActivityResultUpdateComponent,
    ActivityResultDeleteDialogComponent,
  ],
  entryComponents: [ActivityResultDeleteDialogComponent],
})
export class JHipster6105ActivityResultModule {}
