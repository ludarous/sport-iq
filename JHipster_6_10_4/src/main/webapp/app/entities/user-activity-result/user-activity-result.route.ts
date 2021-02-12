import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserActivityResult, UserActivityResult } from 'app/shared/model/user-activity-result.model';
import { UserActivityResultService } from './user-activity-result.service';
import { UserActivityResultComponent } from './user-activity-result.component';
import { UserActivityResultDetailComponent } from './user-activity-result-detail.component';
import { UserActivityResultUpdateComponent } from './user-activity-result-update.component';

@Injectable({ providedIn: 'root' })
export class UserActivityResultResolve implements Resolve<IUserActivityResult> {
  constructor(private service: UserActivityResultService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserActivityResult> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userActivityResult: HttpResponse<UserActivityResult>) => {
          if (userActivityResult.body) {
            return of(userActivityResult.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserActivityResult());
  }
}

export const userActivityResultRoute: Routes = [
  {
    path: '',
    component: UserActivityResultComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'UserActivityResults',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserActivityResultDetailComponent,
    resolve: {
      userActivityResult: UserActivityResultResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserActivityResults',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserActivityResultUpdateComponent,
    resolve: {
      userActivityResult: UserActivityResultResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserActivityResults',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserActivityResultUpdateComponent,
    resolve: {
      userActivityResult: UserActivityResultResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserActivityResults',
    },
    canActivate: [UserRouteAccessService],
  },
];
