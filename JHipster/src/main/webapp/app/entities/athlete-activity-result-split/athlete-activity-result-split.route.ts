import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { AthleteActivityResultSplit } from 'app/shared/model/athlete-activity-result-split.model';
import { AthleteActivityResultSplitService } from './athlete-activity-result-split.service';
import { AthleteActivityResultSplitComponent } from './athlete-activity-result-split.component';
import { AthleteActivityResultSplitDetailComponent } from './athlete-activity-result-split-detail.component';
import { AthleteActivityResultSplitUpdateComponent } from './athlete-activity-result-split-update.component';
import { IAthleteActivityResultSplit } from 'app/shared/model/athlete-activity-result-split.model';

@Injectable({ providedIn: 'root' })
export class AthleteActivityResultSplitResolve implements Resolve<IAthleteActivityResultSplit> {
  constructor(private service: AthleteActivityResultSplitService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAthleteActivityResultSplit> {
    const id = route.params['id'];
    if (id) {
      return this.service
        .find(id)
        .pipe(map((athleteActivityResultSplit: HttpResponse<AthleteActivityResultSplit>) => athleteActivityResultSplit.body));
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
