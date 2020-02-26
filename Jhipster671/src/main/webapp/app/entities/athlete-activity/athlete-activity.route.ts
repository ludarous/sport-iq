import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAthleteActivity, AthleteActivity } from 'app/shared/model/athlete-activity.model';
import { AthleteActivityService } from './athlete-activity.service';
import { AthleteActivityComponent } from './athlete-activity.component';
import { AthleteActivityDetailComponent } from './athlete-activity-detail.component';
import { AthleteActivityUpdateComponent } from './athlete-activity-update.component';

@Injectable({ providedIn: 'root' })
export class AthleteActivityResolve implements Resolve<IAthleteActivity> {
  constructor(private service: AthleteActivityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAthleteActivity> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((athleteActivity: HttpResponse<AthleteActivity>) => {
          if (athleteActivity.body) {
            return of(athleteActivity.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AthleteActivity());
  }
}

export const athleteActivityRoute: Routes = [
  {
    path: '',
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
    path: ':id/view',
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
    path: 'new',
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
    path: ':id/edit',
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
