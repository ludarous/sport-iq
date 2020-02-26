import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAthleteActivityResultSplit, AthleteActivityResultSplit } from 'app/shared/model/athlete-activity-result-split.model';
import { AthleteActivityResultSplitService } from './athlete-activity-result-split.service';
import { AthleteActivityResultSplitComponent } from './athlete-activity-result-split.component';
import { AthleteActivityResultSplitDetailComponent } from './athlete-activity-result-split-detail.component';
import { AthleteActivityResultSplitUpdateComponent } from './athlete-activity-result-split-update.component';

@Injectable({ providedIn: 'root' })
export class AthleteActivityResultSplitResolve implements Resolve<IAthleteActivityResultSplit> {
  constructor(private service: AthleteActivityResultSplitService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAthleteActivityResultSplit> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((athleteActivityResultSplit: HttpResponse<AthleteActivityResultSplit>) => {
          if (athleteActivityResultSplit.body) {
            return of(athleteActivityResultSplit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AthleteActivityResultSplit());
  }
}

export const athleteActivityResultSplitRoute: Routes = [
  {
    path: '',
    component: AthleteActivityResultSplitComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'sportiqApp.athleteActivityResultSplit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AthleteActivityResultSplitDetailComponent,
    resolve: {
      athleteActivityResultSplit: AthleteActivityResultSplitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.athleteActivityResultSplit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AthleteActivityResultSplitUpdateComponent,
    resolve: {
      athleteActivityResultSplit: AthleteActivityResultSplitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.athleteActivityResultSplit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AthleteActivityResultSplitUpdateComponent,
    resolve: {
      athleteActivityResultSplit: AthleteActivityResultSplitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.athleteActivityResultSplit.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
