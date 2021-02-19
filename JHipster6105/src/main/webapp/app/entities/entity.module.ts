import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'agreement',
        loadChildren: () => import('./agreement/agreement.module').then(m => m.JHipster6105AgreementModule),
      },
      {
        path: 'legal-representative',
        loadChildren: () => import('./legal-representative/legal-representative.module').then(m => m.JHipster6105LegalRepresentativeModule),
      },
      {
        path: 'user-properties',
        loadChildren: () => import('./user-properties/user-properties.module').then(m => m.JHipster6105UserPropertiesModule),
      },
      {
        path: 'body-characteristics',
        loadChildren: () => import('./body-characteristics/body-characteristics.module').then(m => m.JHipster6105BodyCharacteristicsModule),
      },
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.JHipster6105CountryModule),
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.JHipster6105AddressModule),
      },
      {
        path: 'membership-role',
        loadChildren: () => import('./membership-role/membership-role.module').then(m => m.JHipster6105MembershipRoleModule),
      },
      {
        path: 'organisation',
        loadChildren: () => import('./organisation/organisation.module').then(m => m.JHipster6105OrganisationModule),
      },
      {
        path: 'organisation-membership',
        loadChildren: () =>
          import('./organisation-membership/organisation-membership.module').then(m => m.JHipster6105OrganisationMembershipModule),
      },
      {
        path: 'group',
        loadChildren: () => import('./group/group.module').then(m => m.JHipster6105GroupModule),
      },
      {
        path: 'group-membership',
        loadChildren: () => import('./group-membership/group-membership.module').then(m => m.JHipster6105GroupMembershipModule),
      },
      {
        path: 'event',
        loadChildren: () => import('./event/event.module').then(m => m.JHipster6105EventModule),
      },
      {
        path: 'activity',
        loadChildren: () => import('./activity/activity.module').then(m => m.JHipster6105ActivityModule),
      },
      {
        path: 'activity-result',
        loadChildren: () => import('./activity-result/activity-result.module').then(m => m.JHipster6105ActivityResultModule),
      },
      {
        path: 'activity-result-split',
        loadChildren: () =>
          import('./activity-result-split/activity-result-split.module').then(m => m.JHipster6105ActivityResultSplitModule),
      },
      {
        path: 'user-event',
        loadChildren: () => import('./user-event/user-event.module').then(m => m.JHipster6105UserEventModule),
      },
      {
        path: 'user-activity',
        loadChildren: () => import('./user-activity/user-activity.module').then(m => m.JHipster6105UserActivityModule),
      },
      {
        path: 'user-activity-result',
        loadChildren: () => import('./user-activity-result/user-activity-result.module').then(m => m.JHipster6105UserActivityResultModule),
      },
      {
        path: 'user-activity-result-split',
        loadChildren: () =>
          import('./user-activity-result-split/user-activity-result-split.module').then(m => m.JHipster6105UserActivityResultSplitModule),
      },
      {
        path: 'event-location',
        loadChildren: () => import('./event-location/event-location.module').then(m => m.JHipster6105EventLocationModule),
      },
      {
        path: 'sport',
        loadChildren: () => import('./sport/sport.module').then(m => m.JHipster6105SportModule),
      },
      {
        path: 'unit',
        loadChildren: () => import('./unit/unit.module').then(m => m.JHipster6105UnitModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JHipster6105EntityModule {}
