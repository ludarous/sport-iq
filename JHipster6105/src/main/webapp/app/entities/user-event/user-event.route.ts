import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserEvent, UserEvent } from 'app/shared/model/user-event.model';
import { UserEventService } from './user-event.service';
import { UserEventComponent } from './user-event.component';
import { UserEventDetailComponent } from './user-event-detail.component';
import { UserEventUpdateComponent } from './user-event-update.component';

@Injectable({ providedIn: 'root' })
export class UserEventResolve implements Resolve<IUserEvent> {
  constructor(private service: UserEventService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserEvent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userEvent: HttpResponse<UserEvent>) => {
          if (userEvent.body) {
            return of(userEvent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserEvent());
  }
}

export const userEventRoute: Routes = [
  {
    path: '',
    component: UserEventComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'UserEvents',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserEventDetailComponent,
    resolve: {
      userEvent: UserEventResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserEvents',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserEventUpdateComponent,
    resolve: {
      userEvent: UserEventResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserEvents',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserEventUpdateComponent,
    resolve: {
      userEvent: UserEventResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserEvents',
    },
    canActivate: [UserRouteAccessService],
  },
];
