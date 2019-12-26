import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Workout } from 'app/shared/model/workout.model';
import { WorkoutService } from './workout.service';
import { WorkoutComponent } from './workout.component';
import { WorkoutDetailComponent } from './workout-detail.component';
import { WorkoutUpdateComponent } from './workout-update.component';
import { IWorkout } from 'app/shared/model/workout.model';

@Injectable({ providedIn: 'root' })
export class WorkoutResolve implements Resolve<IWorkout> {
  constructor(private service: WorkoutService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkout> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((workout: HttpResponse<Workout>) => workout.body));
    }
    return of(new Workout());
  }
}

export const workoutRoute: Routes = [
  {
    path: '',
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
    path: ':id/view',
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
    path: 'new',
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
    path: ':id/edit',
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
