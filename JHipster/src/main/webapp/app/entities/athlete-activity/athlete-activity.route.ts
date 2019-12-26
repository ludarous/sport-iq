import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AthleteActivity } from 'app/shared/model/athlete-activity.model';
import { AthleteActivityService } from './athlete-activity.service';
import { AthleteActivityComponent } from './athlete-activity.component';
import { AthleteActivityDetailComponent } from './athlete-activity-detail.component';
import { AthleteActivityUpdateComponent } from './athlete-activity-update.component';
import { IAthleteActivity } from 'app/shared/model/athlete-activity.model';

@Injectable({ providedIn: 'root' })
export class AthleteActivityResolve implements Resolve<IAthleteActivity> {
  constructor(private service: AthleteActivityService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAthleteActivity> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((athleteActivity: HttpResponse<AthleteActivity>) => athleteActivity.body));
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
