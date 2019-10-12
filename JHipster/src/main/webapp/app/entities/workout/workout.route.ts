import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Workout } from 'app/shared/model/workout.model';
import { WorkoutService } from './workout.service';
import { WorkoutComponent } from './workout.component';
import { WorkoutDetailComponent } from './workout-detail.component';
import { WorkoutUpdateComponent } from './workout-update.component';
import { WorkoutDeletePopupComponent } from './workout-delete-dialog.component';
import { IWorkout } from 'app/shared/model/workout.model';

@Injectable({ providedIn: 'root' })
export class WorkoutResolve implements Resolve<IWorkout> {
    constructor(private service: WorkoutService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((workout: HttpResponse<Workout>) => workout.body));
        }
        return of(new Workout());
    }
}

export const workoutRoute: Routes = [
    {
        path: 'workout',
        component: WorkoutComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'sportiqApp.workout.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'workout/:id/view',
        component: WorkoutDetailComponent,
        resolve: {
            workout: WorkoutResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.workout.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'workout/new',
        component: WorkoutUpdateComponent,
        resolve: {
            workout: WorkoutResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.workout.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'workout/:id/edit',
        component: WorkoutUpdateComponent,
        resolve: {
            workout: WorkoutResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.workout.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const workoutPopupRoute: Routes = [
    {
        path: 'workout/:id/delete',
        component: WorkoutDeletePopupComponent,
        resolve: {
            workout: WorkoutResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sportiqApp.workout.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
