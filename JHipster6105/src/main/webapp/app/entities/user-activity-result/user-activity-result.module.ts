import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipster6105SharedModule } from 'app/shared/shared.module';
import { UserActivityResultComponent } from './user-activity-result.component';
import { UserActivityResultDetailComponent } from './user-activity-result-detail.component';
import { UserActivityResultUpdateComponent } from './user-activity-result-update.component';
import { UserActivityResultDeleteDialogComponent } from './user-activity-result-delete-dialog.component';
import { userActivityResultRoute } from './user-activity-result.route';

@NgModule({
  imports: [JHipster6105SharedModule, RouterModule.forChild(userActivityResultRoute)],
  declarations: [
    UserActivityResultComponent,
    UserActivityResultDetailComponent,
    UserActivityResultUpdateComponent,
    UserActivityResultDeleteDialogComponent,
  ],
  entryComponents: [UserActivityResultDeleteDialogComponent],
})
export class JHipster6105UserActivityResultModule {}
