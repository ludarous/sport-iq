import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AthleteEvent } from 'app/shared/model/athlete-event.model';
import { AthleteEventService } from './athlete-event.service';
import { AthleteEventComponent } from './athlete-event.component';
import { AthleteEventDetailComponent } from './athlete-event-detail.component';
import { AthleteEventUpdateComponent } from './athlete-event-update.component';
import { AthleteEventDeletePopupComponent } from './athlete-event-delete-dialog.component';
import { IAthleteEvent } from 'app/shared/model/athlete-event.model';

@Injectable({ providedIn: 'root' })
export class AthleteEventResolve implements Resolve<IAthleteEvent> {
    constructor(private service: AthleteEventService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((athleteEvent: HttpResponse<AthleteEvent>) => athleteEvent.body));
        }
        return of(new AthleteEvent());
    }
}

export const athleteEventRoute: Routes = [
    {
        path: 'athlete-event',
        component: AthleteEventComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'sportiqApp.athleteEvent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'athlete-event/:id/view',
        component: AthleteEventDetailComponent,
        resolve: {
            athleteEvent: AthleteEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.athleteEvent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'athlete-event/new',
        component: AthleteEventUpdateComponent,
        resolve: {
            athleteEvent: AthleteEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.athleteEvent.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'athlete-event/:id/edit',
        component: AthleteEventUpdateComponent,
        resolve: {
            athleteEvent: AthleteEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.athleteEvent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const athleteEventPopupRoute: Routes = [
    {
        path: 'athlete-event/:id/delete',
        component: AthleteEventDeletePopupComponent,
        resolve: {
            athleteEvent: AthleteEventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.athleteEvent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
