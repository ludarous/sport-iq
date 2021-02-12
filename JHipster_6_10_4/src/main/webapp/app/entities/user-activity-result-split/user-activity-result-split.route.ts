import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserActivityResultSplit, UserActivityResultSplit } from 'app/shared/model/user-activity-result-split.model';
import { UserActivityResultSplitService } from './user-activity-result-split.service';
import { UserActivityResultSplitComponent } from './user-activity-result-split.component';
import { UserActivityResultSplitDetailComponent } from './user-activity-result-split-detail.component';
import { UserActivityResultSplitUpdateComponent } from './user-activity-result-split-update.component';

@Injectable({ providedIn: 'root' })
export class UserActivityResultSplitResolve implements Resolve<IUserActivityResultSplit> {
  constructor(private service: UserActivityResultSplitService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserActivityResultSplit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userActivityResultSplit: HttpResponse<UserActivityResultSplit>) => {
          if (userActivityResultSplit.body) {
            return of(userActivityResultSplit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserActivityResultSplit());
  }
}

export const userActivityResultSplitRoute: Routes = [
  {
    path: '',
    component: UserActivityResultSplitComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'UserActivityResultSplits',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserActivityResultSplitDetailComponent,
    resolve: {
      userActivityResultSplit: UserActivityResultSplitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserActivityResultSplits',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserActivityResultSplitUpdateComponent,
    resolve: {
      userActivityResultSplit: UserActivityResultSplitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserActivityResultSplits',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserActivityResultSplitUpdateComponent,
    resolve: {
      userActivityResultSplit: UserActivityResultSplitResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserActivityResultSplits',
    },
    canActivate: [UserRouteAccessService],
  },
];
