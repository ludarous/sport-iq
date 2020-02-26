import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IActivityCategory, ActivityCategory } from 'app/shared/model/activity-category.model';
import { ActivityCategoryService } from './activity-category.service';
import { ActivityCategoryComponent } from './activity-category.component';
import { ActivityCategoryDetailComponent } from './activity-category-detail.component';
import { ActivityCategoryUpdateComponent } from './activity-category-update.component';

@Injectable({ providedIn: 'root' })
export class ActivityCategoryResolve implements Resolve<IActivityCategory> {
  constructor(private service: ActivityCategoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActivityCategory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((activityCategory: HttpResponse<ActivityCategory>) => {
          if (activityCategory.body) {
            return of(activityCategory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ActivityCategory());
  }
}

export const activityCategoryRoute: Routes = [
  {
    path: '',
    component: ActivityCategoryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'sportiqApp.activityCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ActivityCategoryDetailComponent,
    resolve: {
      activityCategory: ActivityCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.activityCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ActivityCategoryUpdateComponent,
    resolve: {
      activityCategory: ActivityCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.activityCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ActivityCategoryUpdateComponent,
    resolve: {
      activityCategory: ActivityCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.activityCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
