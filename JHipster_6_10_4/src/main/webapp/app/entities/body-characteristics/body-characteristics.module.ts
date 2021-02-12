import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportIqSharedModule } from 'app/shared/shared.module';
import { BodyCharacteristicsComponent } from './body-characteristics.component';
import { BodyCharacteristicsDetailComponent } from './body-characteristics-detail.component';
import { BodyCharacteristicsUpdateComponent } from './body-characteristics-update.component';
import { BodyCharacteristicsDeleteDialogComponent } from './body-characteristics-delete-dialog.component';
import { bodyCharacteristicsRoute } from './body-characteristics.route';

@NgModule({
  imports: [SportIqSharedModule, RouterModule.forChild(bodyCharacteristicsRoute)],
  declarations: [
    BodyCharacteristicsComponent,
    BodyCharacteristicsDetailComponent,
    BodyCharacteristicsUpdateComponent,
    BodyCharacteristicsDeleteDialogComponent,
  ],
  entryComponents: [BodyCharacteristicsDeleteDialogComponent],
})
export class SportIqBodyCharacteristicsModule {}
