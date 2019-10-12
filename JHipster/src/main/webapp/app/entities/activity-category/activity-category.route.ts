import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ActivityCategory } from 'app/shared/model/activity-category.model';
import { ActivityCategoryService } from './activity-category.service';
import { ActivityCategoryComponent } from './activity-category.component';
import { ActivityCategoryDetailComponent } from './activity-category-detail.component';
import { ActivityCategoryUpdateComponent } from './activity-category-update.component';
import { ActivityCategoryDeletePopupComponent } from './activity-category-delete-dialog.component';
import { IActivityCategory } from 'app/shared/model/activity-category.model';

@Injectable({ providedIn: 'root' })
export class ActivityCategoryResolve implements Resolve<IActivityCategory> {
    constructor(private service: ActivityCategoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((activityCategory: HttpResponse<ActivityCategory>) => activityCategory.body));
        }
        return of(new ActivityCategory());
    }
}

export const activityCategoryRoute: Routes = [
    {
        path: 'activity-category',
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
        path: 'activity-category/:id/view',
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
        path: 'activity-category/new',
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
        path: 'activity-category/:id/edit',
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

export const activityCategoryPopupRoute: Routes = [
    {
        path: 'activity-category/:id/delete',
        component: ActivityCategoryDeletePopupComponent,
        resolve: {
            activityCategory: ActivityCategoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.activityCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
