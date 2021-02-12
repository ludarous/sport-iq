import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportIqSharedModule } from 'app/shared/shared.module';
import { GroupMembershipComponent } from './group-membership.component';
import { GroupMembershipDetailComponent } from './group-membership-detail.component';
import { GroupMembershipUpdateComponent } from './group-membership-update.component';
import { GroupMembershipDeleteDialogComponent } from './group-membership-delete-dialog.component';
import { groupMembershipRoute } from './group-membership.route';

@NgModule({
  imports: [SportIqSharedModule, RouterModule.forChild(groupMembershipRoute)],
  declarations: [
    GroupMembershipComponent,
    GroupMembershipDetailComponent,
    GroupMembershipUpdateComponent,
    GroupMembershipDeleteDialogComponent,
  ],
  entryComponents: [GroupMembershipDeleteDialogComponent],
})
export class SportIqGroupMembershipModule {}
