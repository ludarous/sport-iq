import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportIqSharedModule } from 'app/shared/shared.module';
import { SportComponent } from './sport.component';
import { SportDetailComponent } from './sport-detail.component';
import { SportUpdateComponent } from './sport-update.component';
import { SportDeleteDialogComponent } from './sport-delete-dialog.component';
import { sportRoute } from './sport.route';

@NgModule({
  imports: [SportIqSharedModule, RouterModule.forChild(sportRoute)],
  declarations: [SportComponent, SportDetailComponent, SportUpdateComponent, SportDeleteDialogComponent],
  entryComponents: [SportDeleteDialogComponent],
})
export class SportIqSportModule {}
