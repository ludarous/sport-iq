import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportIqSharedModule } from 'app/shared/shared.module';
import { GroupComponent } from './group.component';
import { GroupDetailComponent } from './group-detail.component';
import { GroupUpdateComponent } from './group-update.component';
import { GroupDeleteDialogComponent } from './group-delete-dialog.component';
import { groupRoute } from './group.route';

@NgModule({
  imports: [SportIqSharedModule, RouterModule.forChild(groupRoute)],
  declarations: [GroupComponent, GroupDetailComponent, GroupUpdateComponent, GroupDeleteDialogComponent],
  entryComponents: [GroupDeleteDialogComponent],
})
export class SportIqGroupModule {}
