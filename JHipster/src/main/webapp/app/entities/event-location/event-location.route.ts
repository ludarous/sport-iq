import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { EventLocation } from 'app/shared/model/event-location.model';
import { EventLocationService } from './event-location.service';
import { EventLocationComponent } from './event-location.component';
import { EventLocationDetailComponent } from './event-location-detail.component';
import { EventLocationUpdateComponent } from './event-location-update.component';
import { IEventLocation } from 'app/shared/model/event-location.model';

@Injectable({ providedIn: 'root' })
export class EventLocationResolve implements Resolve<IEventLocation> {
  constructor(private service: EventLocationService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventLocation> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((eventLocation: HttpResponse<EventLocation>) => eventLocation.body));
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
