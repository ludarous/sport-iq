import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipster6105SharedModule } from 'app/shared/shared.module';
import { UserPropertiesComponent } from './user-properties.component';
import { UserPropertiesDetailComponent } from './user-properties-detail.component';
import { UserPropertiesUpdateComponent } from './user-properties-update.component';
import { UserPropertiesDeleteDialogComponent } from './user-properties-delete-dialog.component';
import { userPropertiesRoute } from './user-properties.route';

@NgModule({
  imports: [JHipster6105SharedModule, RouterModule.forChild(userPropertiesRoute)],
  declarations: [
    UserPropertiesComponent,
    UserPropertiesDetailComponent,
    UserPropertiesUpdateComponent,
    UserPropertiesDeleteDialogComponent,
  ],
  entryComponents: [UserPropertiesDeleteDialogComponent],
})
export class JHipster6105UserPropertiesModule {}
