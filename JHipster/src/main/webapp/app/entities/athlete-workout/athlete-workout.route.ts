import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AthleteWorkout } from 'app/shared/model/athlete-workout.model';
import { AthleteWorkoutService } from './athlete-workout.service';
import { AthleteWorkoutComponent } from './athlete-workout.component';
import { AthleteWorkoutDetailComponent } from './athlete-workout-detail.component';
import { AthleteWorkoutUpdateComponent } from './athlete-workout-update.component';
import { IAthleteWorkout } from 'app/shared/model/athlete-workout.model';

@Injectable({ providedIn: 'root' })
export class AthleteWorkoutResolve implements Resolve<IAthleteWorkout> {
  constructor(private service: AthleteWorkoutService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAthleteWorkout> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((athleteWorkout: HttpResponse<AthleteWorkout>) => athleteWorkout.body));
    }
    return of(new AthleteWorkout());
  }
}

export const athleteWorkoutRoute: Routes = [
  {
    path: '',
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
    path: ':id/view',
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
    path: 'new',
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
    path: ':id/edit',
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
