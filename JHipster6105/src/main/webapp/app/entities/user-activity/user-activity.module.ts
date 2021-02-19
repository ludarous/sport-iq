import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipster6105SharedModule } from 'app/shared/shared.module';
import { UserActivityComponent } from './user-activity.component';
import { UserActivityDetailComponent } from './user-activity-detail.component';
import { UserActivityUpdateComponent } from './user-activity-update.component';
import { UserActivityDeleteDialogComponent } from './user-activity-delete-dialog.component';
import { userActivityRoute } from './user-activity.route';

@NgModule({
  imports: [JHipster6105SharedModule, RouterModule.forChild(userActivityRoute)],
  declarations: [UserActivityComponent, UserActivityDetailComponent, UserActivityUpdateComponent, UserActivityDeleteDialogComponent],
  entryComponents: [UserActivityDeleteDialogComponent],
})
export class JHipster6105UserActivityModule {}
