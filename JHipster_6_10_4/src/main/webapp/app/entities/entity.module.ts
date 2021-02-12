import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'agreement',
        loadChildren: () => import('./agreement/agreement.module').then(m => m.SportIqAgreementModule),
      },
      {
        path: 'legal-representative',
        loadChildren: () => import('./legal-representative/legal-representative.module').then(m => m.SportIqLegalRepresentativeModule),
      },
      {
        path: 'user-properties',
        loadChildren: () => import('./user-properties/user-properties.module').then(m => m.SportIqUserPropertiesModule),
      },
      {
        path: 'body-characteristics',
        loadChildren: () => import('./body-characteristics/body-characteristics.module').then(m => m.SportIqBodyCharacteristicsModule),
      },
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.SportIqCountryModule),
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.SportIqAddressModule),
      },
      {
        path: 'membership-role',
        loadChildren: () => import('./membership-role/membership-role.module').then(m => m.SportIqMembershipRoleModule),
      },
      {
        path: 'organisation',
        loadChildren: () => import('./organisation/organisation.module').then(m => m.SportIqOrganisationModule),
      },
      {
        path: 'organisation-membership',
        loadChildren: () =>
          import('./organisation-membership/organisation-membership.module').then(m => m.SportIqOrganisationMembershipModule),
      },
      {
        path: 'group',
        loadChildren: () => import('./group/group.module').then(m => m.SportIqGroupModule),
      },
      {
        path: 'group-membership',
        loadChildren: () => import('./group-membership/group-membership.module').then(m => m.SportIqGroupMembershipModule),
      },
      {
        path: 'event',
        loadChildren: () => import('./event/event.module').then(m => m.SportIqEventModule),
      },
      {
        path: 'activity',
        loadChildren: () => import('./activity/activity.module').then(m => m.SportIqActivityModule),
      },
      {
        path: 'activity-result',
        loadChildren: () => import('./activity-result/activity-result.module').then(m => m.SportIqActivityResultModule),
      },
      {
        path: 'activity-result-split',
        loadChildren: () => import('./activity-result-split/activity-result-split.module').then(m => m.SportIqActivityResultSplitModule),
      },
      {
        path: 'user-event',
        loadChildren: () => import('./user-event/user-event.module').then(m => m.SportIqUserEventModule),
      },
      {
        path: 'user-activity',
        loadChildren: () => import('./user-activity/user-activity.module').then(m => m.SportIqUserActivityModule),
      },
      {
        path: 'user-activity-result',
        loadChildren: () => import('./user-activity-result/user-activity-result.module').then(m => m.SportIqUserActivityResultModule),
      },
      {
        path: 'user-activity-result-split',
        loadChildren: () =>
          import('./user-activity-result-split/user-activity-result-split.module').then(m => m.SportIqUserActivityResultSplitModule),
      },
      {
        path: 'event-location',
        loadChildren: () => import('./event-location/event-location.module').then(m => m.SportIqEventLocationModule),
      },
      {
        path: 'sport',
        loadChildren: () => import('./sport/sport.module').then(m => m.SportIqSportModule),
      },
      {
        path: 'unit',
        loadChildren: () => import('./unit/unit.module').then(m => m.SportIqUnitModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SportIqEntityModule {}
