import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ActivityResult } from 'app/shared/model/activity-result.model';
import { ActivityResultService } from './activity-result.service';
import { ActivityResultComponent } from './activity-result.component';
import { ActivityResultDetailComponent } from './activity-result-detail.component';
import { ActivityResultUpdateComponent } from './activity-result-update.component';
import { IActivityResult } from 'app/shared/model/activity-result.model';

@Injectable({ providedIn: 'root' })
export class ActivityResultResolve implements Resolve<IActivityResult> {
  constructor(private service: ActivityResultService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActivityResult> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((activityResult: HttpResponse<ActivityResult>) => activityResult.body));
    }
    return of(new ActivityResult());
  }
}

export const activityResultRoute: Routes = [
  {
    path: '',
    component: ActivityResultComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'sportiqApp.activityResult.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ActivityResultDetailComponent,
    resolve: {
      activityResult: ActivityResultResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.activityResult.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ActivityResultUpdateComponent,
    resolve: {
      activityResult: ActivityResultResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.activityResult.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ActivityResultUpdateComponent,
    resolve: {
      activityResult: ActivityResultResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.activityResult.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
