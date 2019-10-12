import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AthleteActivityResult } from 'app/shared/model/athlete-activity-result.model';
import { AthleteActivityResultService } from './athlete-activity-result.service';
import { AthleteActivityResultComponent } from './athlete-activity-result.component';
import { AthleteActivityResultDetailComponent } from './athlete-activity-result-detail.component';
import { AthleteActivityResultUpdateComponent } from './athlete-activity-result-update.component';
import { AthleteActivityResultDeletePopupComponent } from './athlete-activity-result-delete-dialog.component';
import { IAthleteActivityResult } from 'app/shared/model/athlete-activity-result.model';

@Injectable({ providedIn: 'root' })
export class AthleteActivityResultResolve implements Resolve<IAthleteActivityResult> {
    constructor(private service: AthleteActivityResultService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service
                .find(id)
                .pipe(map((athleteActivityResult: HttpResponse<AthleteActivityResult>) => athleteActivityResult.body));
        }
        return of(new AthleteActivityResult());
    }
}

export const athleteActivityResultRoute: Routes = [
    {
        path: 'athlete-activity-result',
        component: AthleteActivityResultComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'sportiqApp.athleteActivityResult.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'athlete-activity-result/:id/view',
        component: AthleteActivityResultDetailComponent,
        resolve: {
            athleteActivityResult: AthleteActivityResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.athleteActivityResult.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'athlete-activity-result/new',
        component: AthleteActivityResultUpdateComponent,
        resolve: {
            athleteActivityResult: AthleteActivityResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.athleteActivityResult.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'athlete-activity-result/:id/edit',
        component: AthleteActivityResultUpdateComponent,
        resolve: {
            athleteActivityResult: AthleteActivityResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.athleteActivityResult.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const athleteActivityResultPopupRoute: Routes = [
    {
        path: 'athlete-activity-result/:id/delete',
        component: AthleteActivityResultDeletePopupComponent,
        resolve: {
            athleteActivityResult: AthleteActivityResultResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.athleteActivityResult.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
