import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEventLocation, EventLocation } from 'app/shared/model/event-location.model';
import { EventLocationService } from './event-location.service';
import { EventLocationComponent } from './event-location.component';
import { EventLocationDetailComponent } from './event-location-detail.component';
import { EventLocationUpdateComponent } from './event-location-update.component';

@Injectable({ providedIn: 'root' })
export class EventLocationResolve implements Resolve<IEventLocation> {
  constructor(private service: EventLocationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventLocation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((eventLocation: HttpResponse<EventLocation>) => {
          if (eventLocation.body) {
            return of(eventLocation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EventLocation());
  }
}

export const eventLocationRoute: Routes = [
  {
    path: '',
    component: EventLocationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'sportiqApp.eventLocation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EventLocationDetailComponent,
    resolve: {
      eventLocation: EventLocationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.eventLocation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EventLocationUpdateComponent,
    resolve: {
      eventLocation: EventLocationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.eventLocation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EventLocationUpdateComponent,
    resolve: {
      eventLocation: EventLocationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.eventLocation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
