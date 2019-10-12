import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ActivityResult } from 'app/shared/model/activity-result.model';
import { ActivityResultService } from './activity-result.service';
import { ActivityResultComponent } from './activity-result.component';
import { ActivityResultDetailComponent } from './activity-result-detail.component';
import { ActivityResultUpdateComponent } from './activity-result-update.component';
import { ActivityResultDeletePopupComponent } from './activity-result-delete-dialog.component';
import { IActivityResult } from 'app/shared/model/activity-result.model';

@Injectable({ providedIn: 'root' })
export class ActivityResultResolve implements Resolve<IActivityResult> {
    constructor(private service: ActivityResultService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((activityResult: HttpResponse<ActivityResult>) => activityResult.body));
        }
        return of(new ActivityResult());
    }
}

export const activityResultRoute: Routes = [
    {
        path: 'activity-result',
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
        path: 'activity-result/:id/view',
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
        path: 'activity-result/new',
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
        path: 'activity-result/:id/edit',
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

export const activityResultPopupRoute: Routes = [
    {
        path: 'activity-result/:id/delete',
        component: ActivityResultDeletePopupComponent,
        resolve: {
            activityResult: ActivityResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.activityResult.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
