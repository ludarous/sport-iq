import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMembershipRole, MembershipRole } from 'app/shared/model/membership-role.model';
import { MembershipRoleService } from './membership-role.service';
import { MembershipRoleComponent } from './membership-role.component';
import { MembershipRoleDetailComponent } from './membership-role-detail.component';
import { MembershipRoleUpdateComponent } from './membership-role-update.component';

@Injectable({ providedIn: 'root' })
export class MembershipRoleResolve implements Resolve<IMembershipRole> {
  constructor(private service: MembershipRoleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMembershipRole> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((membershipRole: HttpResponse<MembershipRole>) => {
          if (membershipRole.body) {
            return of(membershipRole.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MembershipRole());
  }
}

export const membershipRoleRoute: Routes = [
  {
    path: '',
    component: MembershipRoleComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'MembershipRoles',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MembershipRoleDetailComponent,
    resolve: {
      membershipRole: MembershipRoleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MembershipRoles',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MembershipRoleUpdateComponent,
    resolve: {
      membershipRole: MembershipRoleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MembershipRoles',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MembershipRoleUpdateComponent,
    resolve: {
      membershipRole: MembershipRoleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MembershipRoles',
    },
    canActivate: [UserRouteAccessService],
  },
];
