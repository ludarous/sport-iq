import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserActivity, UserActivity } from 'app/shared/model/user-activity.model';
import { UserActivityService } from './user-activity.service';
import { UserActivityComponent } from './user-activity.component';
import { UserActivityDetailComponent } from './user-activity-detail.component';
import { UserActivityUpdateComponent } from './user-activity-update.component';

@Injectable({ providedIn: 'root' })
export class UserActivityResolve implements Resolve<IUserActivity> {
  constructor(private service: UserActivityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserActivity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userActivity: HttpResponse<UserActivity>) => {
          if (userActivity.body) {
            return of(userActivity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserActivity());
  }
}

export const userActivityRoute: Routes = [
  {
    path: '',
    component: UserActivityComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'UserActivities',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserActivityDetailComponent,
    resolve: {
      userActivity: UserActivityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserActivities',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserActivityUpdateComponent,
    resolve: {
      userActivity: UserActivityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserActivities',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserActivityUpdateComponent,
    resolve: {
      userActivity: UserActivityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserActivities',
    },
    canActivate: [UserRouteAccessService],
  },
];
