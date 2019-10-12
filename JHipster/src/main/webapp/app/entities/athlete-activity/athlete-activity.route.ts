import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AthleteActivity } from 'app/shared/model/athlete-activity.model';
import { AthleteActivityService } from './athlete-activity.service';
import { AthleteActivityComponent } from './athlete-activity.component';
import { AthleteActivityDetailComponent } from './athlete-activity-detail.component';
import { AthleteActivityUpdateComponent } from './athlete-activity-update.component';
import { AthleteActivityDeletePopupComponent } from './athlete-activity-delete-dialog.component';
import { IAthleteActivity } from 'app/shared/model/athlete-activity.model';

@Injectable({ providedIn: 'root' })
export class AthleteActivityResolve implements Resolve<IAthleteActivity> {
    constructor(private service: AthleteActivityService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((athleteActivity: HttpResponse<AthleteActivity>) => athleteActivity.body));
        }
        return of(new AthleteActivity());
    }
}

export const athleteActivityRoute: Routes = [
    {
        path: 'athlete-activity',
        component: AthleteActivityComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'sportiqApp.athleteActivity.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'athlete-activity/:id/view',
        component: AthleteActivityDetailComponent,
        resolve: {
            athleteActivity: AthleteActivityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.athleteActivity.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'athlete-activity/new',
        component: AthleteActivityUpdateComponent,
        resolve: {
            athleteActivity: AthleteActivityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.athleteActivity.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'athlete-activity/:id/edit',
        component: AthleteActivityUpdateComponent,
        resolve: {
            athleteActivity: AthleteActivityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.athleteActivity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const athleteActivityPopupRoute: Routes = [
    {
        path: 'athlete-activity/:id/delete',
        component: AthleteActivityDeletePopupComponent,
        resolve: {
            athleteActivity: AthleteActivityResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.athleteActivity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
