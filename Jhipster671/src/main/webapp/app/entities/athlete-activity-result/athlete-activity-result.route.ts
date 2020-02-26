import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAthleteActivityResult, AthleteActivityResult } from 'app/shared/model/athlete-activity-result.model';
import { AthleteActivityResultService } from './athlete-activity-result.service';
import { AthleteActivityResultComponent } from './athlete-activity-result.component';
import { AthleteActivityResultDetailComponent } from './athlete-activity-result-detail.component';
import { AthleteActivityResultUpdateComponent } from './athlete-activity-result-update.component';

@Injectable({ providedIn: 'root' })
export class AthleteActivityResultResolve implements Resolve<IAthleteActivityResult> {
  constructor(private service: AthleteActivityResultService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAthleteActivityResult> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((athleteActivityResult: HttpResponse<AthleteActivityResult>) => {
          if (athleteActivityResult.body) {
            return of(athleteActivityResult.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AthleteActivityResult());
  }
}

export const athleteActivityResultRoute: Routes = [
  {
    path: '',
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
    path: ':id/view',
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
    path: 'new',
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
    path: ':id/edit',
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
