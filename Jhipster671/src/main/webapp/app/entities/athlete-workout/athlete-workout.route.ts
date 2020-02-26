import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAthleteWorkout, AthleteWorkout } from 'app/shared/model/athlete-workout.model';
import { AthleteWorkoutService } from './athlete-workout.service';
import { AthleteWorkoutComponent } from './athlete-workout.component';
import { AthleteWorkoutDetailComponent } from './athlete-workout-detail.component';
import { AthleteWorkoutUpdateComponent } from './athlete-workout-update.component';

@Injectable({ providedIn: 'root' })
export class AthleteWorkoutResolve implements Resolve<IAthleteWorkout> {
  constructor(private service: AthleteWorkoutService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAthleteWorkout> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((athleteWorkout: HttpResponse<AthleteWorkout>) => {
          if (athleteWorkout.body) {
            return of(athleteWorkout.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
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
