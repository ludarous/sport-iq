import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBodyCharacteristics, BodyCharacteristics } from 'app/shared/model/body-characteristics.model';
import { BodyCharacteristicsService } from './body-characteristics.service';
import { BodyCharacteristicsComponent } from './body-characteristics.component';
import { BodyCharacteristicsDetailComponent } from './body-characteristics-detail.component';
import { BodyCharacteristicsUpdateComponent } from './body-characteristics-update.component';

@Injectable({ providedIn: 'root' })
export class BodyCharacteristicsResolve implements Resolve<IBodyCharacteristics> {
  constructor(private service: BodyCharacteristicsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBodyCharacteristics> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((bodyCharacteristics: HttpResponse<BodyCharacteristics>) => {
          if (bodyCharacteristics.body) {
            return of(bodyCharacteristics.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BodyCharacteristics());
  }
}

export const bodyCharacteristicsRoute: Routes = [
  {
    path: '',
    component: BodyCharacteristicsComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'BodyCharacteristics',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BodyCharacteristicsDetailComponent,
    resolve: {
      bodyCharacteristics: BodyCharacteristicsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BodyCharacteristics',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BodyCharacteristicsUpdateComponent,
    resolve: {
      bodyCharacteristics: BodyCharacteristicsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BodyCharacteristics',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BodyCharacteristicsUpdateComponent,
    resolve: {
      bodyCharacteristics: BodyCharacteristicsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BodyCharacteristics',
    },
    canActivate: [UserRouteAccessService],
  },
];
