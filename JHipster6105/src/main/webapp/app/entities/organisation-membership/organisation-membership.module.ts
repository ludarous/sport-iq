import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipster6105SharedModule } from 'app/shared/shared.module';
import { OrganisationMembershipComponent } from './organisation-membership.component';
import { OrganisationMembershipDetailComponent } from './organisation-membership-detail.component';
import { OrganisationMembershipUpdateComponent } from './organisation-membership-update.component';
import { OrganisationMembershipDeleteDialogComponent } from './organisation-membership-delete-dialog.component';
import { organisationMembershipRoute } from './organisation-membership.route';

@NgModule({
  imports: [JHipster6105SharedModule, RouterModule.forChild(organisationMembershipRoute)],
  declarations: [
    OrganisationMembershipComponent,
    OrganisationMembershipDetailComponent,
    OrganisationMembershipUpdateComponent,
    OrganisationMembershipDeleteDialogComponent,
  ],
  entryComponents: [OrganisationMembershipDeleteDialogComponent],
})
export class JHipster6105OrganisationMembershipModule {}
