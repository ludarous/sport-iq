import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipster6105SharedModule } from 'app/shared/shared.module';
import { MembershipRoleComponent } from './membership-role.component';
import { MembershipRoleDetailComponent } from './membership-role-detail.component';
import { MembershipRoleUpdateComponent } from './membership-role-update.component';
import { MembershipRoleDeleteDialogComponent } from './membership-role-delete-dialog.component';
import { membershipRoleRoute } from './membership-role.route';

@NgModule({
  imports: [JHipster6105SharedModule, RouterModule.forChild(membershipRoleRoute)],
  declarations: [
    MembershipRoleComponent,
    MembershipRoleDetailComponent,
    MembershipRoleUpdateComponent,
    MembershipRoleDeleteDialogComponent,
  ],
  entryComponents: [MembershipRoleDeleteDialogComponent],
})
export class JHipster6105MembershipRoleModule {}
