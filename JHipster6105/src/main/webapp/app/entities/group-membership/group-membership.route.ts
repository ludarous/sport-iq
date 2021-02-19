import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGroupMembership, GroupMembership } from 'app/shared/model/group-membership.model';
import { GroupMembershipService } from './group-membership.service';
import { GroupMembershipComponent } from './group-membership.component';
import { GroupMembershipDetailComponent } from './group-membership-detail.component';
import { GroupMembershipUpdateComponent } from './group-membership-update.component';

@Injectable({ providedIn: 'root' })
export class GroupMembershipResolve implements Resolve<IGroupMembership> {
  constructor(private service: GroupMembershipService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGroupMembership> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((groupMembership: HttpResponse<GroupMembership>) => {
          if (groupMembership.body) {
            return of(groupMembership.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new GroupMembership());
  }
}

export const groupMembershipRoute: Routes = [
  {
    path: '',
    component: GroupMembershipComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'GroupMemberships',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: GroupMembershipDetailComponent,
    resolve: {
      groupMembership: GroupMembershipResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GroupMemberships',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: GroupMembershipUpdateComponent,
    resolve: {
      groupMembership: GroupMembershipResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GroupMemberships',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: GroupMembershipUpdateComponent,
    resolve: {
      groupMembership: GroupMembershipResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'GroupMemberships',
    },
    canActivate: [UserRouteAccessService],
  },
];
