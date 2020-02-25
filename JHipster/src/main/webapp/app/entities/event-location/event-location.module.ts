import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared/shared.module';
import { EventLocationComponent } from './event-location.component';
import { EventLocationDetailComponent } from './event-location-detail.component';
import { EventLocationUpdateComponent } from './event-location-update.component';
import { EventLocationDeleteDialogComponent } from './event-location-delete-dialog.component';
import { eventLocationRoute } from './event-location.route';

@NgModule({
  imports: [SportiqSharedModule, RouterModule.forChild(eventLocationRoute)],
  declarations: [EventLocationComponent, EventLocationDetailComponent, EventLocationUpdateComponent, EventLocationDeleteDialogComponent],
  entryComponents: [EventLocationDeleteDialogComponent]
})
export class SportiqEventLocationModule {}