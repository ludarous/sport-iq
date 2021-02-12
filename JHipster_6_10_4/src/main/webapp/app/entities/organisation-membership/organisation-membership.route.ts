import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrganisationMembership, OrganisationMembership } from 'app/shared/model/organisation-membership.model';
import { OrganisationMembershipService } from './organisation-membership.service';
import { OrganisationMembershipComponent } from './organisation-membership.component';
import { OrganisationMembershipDetailComponent } from './organisation-membership-detail.component';
import { OrganisationMembershipUpdateComponent } from './organisation-membership-update.component';

@Injectable({ providedIn: 'root' })
export class OrganisationMembershipResolve implements Resolve<IOrganisationMembership> {
  constructor(private service: OrganisationMembershipService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrganisationMembership> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((organisationMembership: HttpResponse<OrganisationMembership>) => {
          if (organisationMembership.body) {
            return of(organisationMembership.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrganisationMembership());
  }
}

export const organisationMembershipRoute: Routes = [
  {
    path: '',
    component: OrganisationMembershipComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'OrganisationMemberships',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrganisationMembershipDetailComponent,
    resolve: {
      organisationMembership: OrganisationMembershipResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'OrganisationMemberships',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrganisationMembershipUpdateComponent,
    resolve: {
      organisationMembership: OrganisationMembershipResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'OrganisationMemberships',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrganisationMembershipUpdateComponent,
    resolve: {
      organisationMembership: OrganisationMembershipResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'OrganisationMemberships',
    },
    canActivate: [UserRouteAccessService],
  },
];
