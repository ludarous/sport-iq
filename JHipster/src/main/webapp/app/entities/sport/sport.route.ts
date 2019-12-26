import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Sport } from 'app/shared/model/sport.model';
import { SportService } from './sport.service';
import { SportComponent } from './sport.component';
import { SportDetailComponent } from './sport-detail.component';
import { SportUpdateComponent } from './sport-update.component';
import { ISport } from 'app/shared/model/sport.model';

@Injectable({ providedIn: 'root' })
export class SportResolve implements Resolve<ISport> {
  constructor(private service: SportService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISport> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((sport: HttpResponse<Sport>) => sport.body));
    }
    return of(new Sport());
  }
}

export const sportRoute: Routes = [
  {
    path: '',
    component: SportComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'sportiqApp.sport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SportDetailComponent,
    resolve: {
      sport: SportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.sport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SportUpdateComponent,
    resolve: {
      sport: SportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.sport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SportUpdateComponent,
    resolve: {
      sport: SportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.sport.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
