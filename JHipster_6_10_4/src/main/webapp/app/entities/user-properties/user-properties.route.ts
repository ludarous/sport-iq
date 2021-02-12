import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserProperties, UserProperties } from 'app/shared/model/user-properties.model';
import { UserPropertiesService } from './user-properties.service';
import { UserPropertiesComponent } from './user-properties.component';
import { UserPropertiesDetailComponent } from './user-properties-detail.component';
import { UserPropertiesUpdateComponent } from './user-properties-update.component';

@Injectable({ providedIn: 'root' })
export class UserPropertiesResolve implements Resolve<IUserProperties> {
  constructor(private service: UserPropertiesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserProperties> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userProperties: HttpResponse<UserProperties>) => {
          if (userProperties.body) {
            return of(userProperties.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserProperties());
  }
}

export const userPropertiesRoute: Routes = [
  {
    path: '',
    component: UserPropertiesComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'UserProperties',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserPropertiesDetailComponent,
    resolve: {
      userProperties: UserPropertiesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserProperties',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserPropertiesUpdateComponent,
    resolve: {
      userProperties: UserPropertiesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserProperties',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserPropertiesUpdateComponent,
    resolve: {
      userProperties: UserPropertiesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'UserProperties',
    },
    canActivate: [UserRouteAccessService],
  },
];
