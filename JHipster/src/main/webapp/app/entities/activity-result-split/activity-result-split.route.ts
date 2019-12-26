import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ActivityResultSplit } from 'app/shared/model/activity-result-split.model';
import { ActivityResultSplitService } from './activity-result-split.service';
import { ActivityResultSplitComponent } from './activity-result-split.component';
import { ActivityResultSplitDetailComponent } from './activity-result-split-detail.component';
import { ActivityResultSplitUpdateComponent } from './activity-result-split-update.component';
import { IActivityResultSplit } from 'app/shared/model/activity-result-split.model';

@Injectable({ providedIn: 'root' })
export class ActivityResultSplitResolve implements Resolve<IActivityResultSplit> {
  constructor(private service: ActivityResultSplitService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActivityResultSplit> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((activityResultSplit: HttpResponse<ActivityResultSplit>) => activityResultSplit.body));
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
