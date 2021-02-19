import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipster6105SharedModule } from 'app/shared/shared.module';
import { UserEventComponent } from './user-event.component';
import { UserEventDetailComponent } from './user-event-detail.component';
import { UserEventUpdateComponent } from './user-event-update.component';
import { UserEventDeleteDialogComponent } from './user-event-delete-dialog.component';
import { userEventRoute } from './user-event.route';

@NgModule({
  imports: [JHipster6105SharedModule, RouterModule.forChild(userEventRoute)],
  declarations: [UserEventComponent, UserEventDetailComponent, UserEventUpdateComponent, UserEventDeleteDialogComponent],
  entryComponents: [UserEventDeleteDialogComponent],
})
export class JHipster6105UserEventModule {}
