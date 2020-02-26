import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IActivityResultSplit, ActivityResultSplit } from 'app/shared/model/activity-result-split.model';
import { ActivityResultSplitService } from './activity-result-split.service';
import { ActivityResultSplitComponent } from './activity-result-split.component';
import { ActivityResultSplitDetailComponent } from './activity-result-split-detail.component';
import { ActivityResultSplitUpdateComponent } from './activity-result-split-update.component';

@Injectable({ providedIn: 'root' })
export class ActivityResultSplitResolve implements Resolve<IActivityResultSplit> {
  constructor(private service: ActivityResultSplitService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActivityResultSplit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((activityResultSplit: HttpResponse<ActivityResultSplit>) => {
          if (activityResultSplit.body) {
            return of(activityResultSplit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ActivityResultSplit());
  }
}

export const activityResultSplitRoute: Routes = [
  {
    path: '',
    component: ActivityResultSplitComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'sportiqApp.activityResultSplit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ActivityResultSplitDetailComponent,
    resolve: {
      activityResultSplit: ActivityResultSplitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.activityResultSplit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ActivityResultSplitUpdateComponent,
    resolve: {
      activityResultSplit: ActivityResultSplitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.activityResultSplit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ActivityResultSplitUpdateComponent,
    resolve: {
      activityResultSplit: ActivityResultSplitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.activityResultSplit.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
