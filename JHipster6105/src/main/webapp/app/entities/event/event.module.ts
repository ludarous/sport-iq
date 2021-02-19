import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipster6105SharedModule } from 'app/shared/shared.module';
import { EventComponent } from './event.component';
import { EventDetailComponent } from './event-detail.component';
import { EventUpdateComponent } from './event-update.component';
import { EventDeleteDialogComponent } from './event-delete-dialog.component';
import { eventRoute } from './event.route';

@NgModule({
  imports: [JHipster6105SharedModule, RouterModule.forChild(eventRoute)],
  declarations: [EventComponent, EventDetailComponent, EventUpdateComponent, EventDeleteDialogComponent],
  entryComponents: [EventDeleteDialogComponent],
})
export class JHipster6105EventModule {}
