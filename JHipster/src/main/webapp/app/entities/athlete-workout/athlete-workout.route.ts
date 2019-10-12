import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AthleteWorkout } from 'app/shared/model/athlete-workout.model';
import { AthleteWorkoutService } from './athlete-workout.service';
import { AthleteWorkoutComponent } from './athlete-workout.component';
import { AthleteWorkoutDetailComponent } from './athlete-workout-detail.component';
import { AthleteWorkoutUpdateComponent } from './athlete-workout-update.component';
import { AthleteWorkoutDeletePopupComponent } from './athlete-workout-delete-dialog.component';
import { IAthleteWorkout } from 'app/shared/model/athlete-workout.model';

@Injectable({ providedIn: 'root' })
export class AthleteWorkoutResolve implements Resolve<IAthleteWorkout> {
    constructor(private service: AthleteWorkoutService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((athleteWorkout: HttpResponse<AthleteWorkout>) => athleteWorkout.body));
        }
        return of(new AthleteWorkout());
    }
}

export const athleteWorkoutRoute: Routes = [
    {
        path: 'athlete-workout',
        component: AthleteWorkoutComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'sportiqApp.athleteWorkout.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'athlete-workout/:id/view',
        component: AthleteWorkoutDetailComponent,
        resolve: {
            athleteWorkout: AthleteWorkoutResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.athleteWorkout.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'athlete-workout/new',
        component: AthleteWorkoutUpdateComponent,
        resolve: {
            athleteWorkout: AthleteWorkoutResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.athleteWorkout.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'athlete-workout/:id/edit',
        component: AthleteWorkoutUpdateComponent,
        resolve: {
            athleteWorkout: AthleteWorkoutResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.athleteWorkout.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const athleteWorkoutPopupRoute: Routes = [
    {
        path: 'athlete-workout/:id/delete',
        component: AthleteWorkoutDeletePopupComponent,
        resolve: {
            athleteWorkout: AthleteWorkoutResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.athleteWorkout.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
