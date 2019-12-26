import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared/shared.module';
import { AthleteEventComponent } from './athlete-event.component';
import { AthleteEventDetailComponent } from './athlete-event-detail.component';
import { AthleteEventUpdateComponent } from './athlete-event-update.component';
import { AthleteEventDeleteDialogComponent } from './athlete-event-delete-dialog.component';
import { athleteEventRoute } from './athlete-event.route';

@NgModule({
  imports: [SportiqSharedModule, RouterModule.forChild(athleteEventRoute)],
  declarations: [AthleteEventComponent, AthleteEventDetailComponent, AthleteEventUpdateComponent, AthleteEventDeleteDialogComponent],
  entryComponents: [AthleteEventDeleteDialogComponent]
})
export class SportiqAthleteEventModule {}
