import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ActivityResultSplit } from 'app/shared/model/activity-result-split.model';
import { ActivityResultSplitService } from './activity-result-split.service';
import { ActivityResultSplitComponent } from './activity-result-split.component';
import { ActivityResultSplitDetailComponent } from './activity-result-split-detail.component';
import { ActivityResultSplitUpdateComponent } from './activity-result-split-update.component';
import { ActivityResultSplitDeletePopupComponent } from './activity-result-split-delete-dialog.component';
import { IActivityResultSplit } from 'app/shared/model/activity-result-split.model';

@Injectable({ providedIn: 'root' })
export class ActivityResultSplitResolve implements Resolve<IActivityResultSplit> {
    constructor(private service: ActivityResultSplitService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((activityResultSplit: HttpResponse<ActivityResultSplit>) => activityResultSplit.body));
        }
        return of(new ActivityResultSplit());
    }
}

export const activityResultSplitRoute: Routes = [
    {
        path: 'activity-result-split',
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
        path: 'activity-result-split/:id/view',
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
        path: 'activity-result-split/new',
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
        path: 'activity-result-split/:id/edit',
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

export const activityResultSplitPopupRoute: Routes = [
    {
        path: 'activity-result-split/:id/delete',
        component: ActivityResultSplitDeletePopupComponent,
        resolve: {
            activityResultSplit: ActivityResultSplitResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.activityResultSplit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
