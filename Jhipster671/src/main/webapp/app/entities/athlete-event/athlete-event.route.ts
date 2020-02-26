import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAthleteEvent, AthleteEvent } from 'app/shared/model/athlete-event.model';
import { AthleteEventService } from './athlete-event.service';
import { AthleteEventComponent } from './athlete-event.component';
import { AthleteEventDetailComponent } from './athlete-event-detail.component';
import { AthleteEventUpdateComponent } from './athlete-event-update.component';

@Injectable({ providedIn: 'root' })
export class AthleteEventResolve implements Resolve<IAthleteEvent> {
  constructor(private service: AthleteEventService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAthleteEvent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((athleteEvent: HttpResponse<AthleteEvent>) => {
          if (athleteEvent.body) {
            return of(athleteEvent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AthleteEvent());
  }
}

export const athleteEventRoute: Routes = [
  {
    path: '',
    component: AthleteEventComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'sportiqApp.athleteEvent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AthleteEventDetailComponent,
    resolve: {
      athleteEvent: AthleteEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.athleteEvent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AthleteEventUpdateComponent,
    resolve: {
      athleteEvent: AthleteEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.athleteEvent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AthleteEventUpdateComponent,
    resolve: {
      athleteEvent: AthleteEventResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.athleteEvent.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
