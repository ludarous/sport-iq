import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IActivityResult, ActivityResult } from 'app/shared/model/activity-result.model';
import { ActivityResultService } from './activity-result.service';
import { ActivityResultComponent } from './activity-result.component';
import { ActivityResultDetailComponent } from './activity-result-detail.component';
import { ActivityResultUpdateComponent } from './activity-result-update.component';

@Injectable({ providedIn: 'root' })
export class ActivityResultResolve implements Resolve<IActivityResult> {
  constructor(private service: ActivityResultService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActivityResult> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((activityResult: HttpResponse<ActivityResult>) => {
          if (activityResult.body) {
            return of(activityResult.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ActivityResult());
  }
}

export const activityResultRoute: Routes = [
  {
    path: '',
    component: ActivityResultComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ActivityResults',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ActivityResultDetailComponent,
    resolve: {
      activityResult: ActivityResultResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ActivityResults',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ActivityResultUpdateComponent,
    resolve: {
      activityResult: ActivityResultResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ActivityResults',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ActivityResultUpdateComponent,
    resolve: {
      activityResult: ActivityResultResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ActivityResults',
    },
    canActivate: [UserRouteAccessService],
  },
];
