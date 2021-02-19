import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOrganisation, Organisation } from 'app/shared/model/organisation.model';
import { OrganisationService } from './organisation.service';
import { OrganisationComponent } from './organisation.component';
import { OrganisationDetailComponent } from './organisation-detail.component';
import { OrganisationUpdateComponent } from './organisation-update.component';

@Injectable({ providedIn: 'root' })
export class OrganisationResolve implements Resolve<IOrganisation> {
  constructor(private service: OrganisationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrganisation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((organisation: HttpResponse<Organisation>) => {
          if (organisation.body) {
            return of(organisation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Organisation());
  }
}

export const organisationRoute: Routes = [
  {
    path: '',
    component: OrganisationComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Organisations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrganisationDetailComponent,
    resolve: {
      organisation: OrganisationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Organisations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrganisationUpdateComponent,
    resolve: {
      organisation: OrganisationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Organisations',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrganisationUpdateComponent,
    resolve: {
      organisation: OrganisationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Organisations',
    },
    canActivate: [UserRouteAccessService],
  },
];
