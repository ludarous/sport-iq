import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ActivityCategory } from 'app/shared/model/activity-category.model';
import { ActivityCategoryService } from './activity-category.service';
import { ActivityCategoryComponent } from './activity-category.component';
import { ActivityCategoryDetailComponent } from './activity-category-detail.component';
import { ActivityCategoryUpdateComponent } from './activity-category-update.component';
import { IActivityCategory } from 'app/shared/model/activity-category.model';

@Injectable({ providedIn: 'root' })
export class ActivityCategoryResolve implements Resolve<IActivityCategory> {
  constructor(private service: ActivityCategoryService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActivityCategory> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((activityCategory: HttpResponse<ActivityCategory>) => activityCategory.body));
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
