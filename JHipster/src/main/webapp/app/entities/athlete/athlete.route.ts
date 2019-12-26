import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Athlete } from 'app/shared/model/athlete.model';
import { AthleteService } from './athlete.service';
import { AthleteComponent } from './athlete.component';
import { AthleteDetailComponent } from './athlete-detail.component';
import { AthleteUpdateComponent } from './athlete-update.component';
import { IAthlete } from 'app/shared/model/athlete.model';

@Injectable({ providedIn: 'root' })
export class AthleteResolve implements Resolve<IAthlete> {
  constructor(private service: AthleteService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAthlete> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((athlete: HttpResponse<Athlete>) => athlete.body));
    }
    return of(new Athlete());
  }
}

export const athleteRoute: Routes = [
  {
    path: '',
    component: AthleteComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'sportiqApp.athlete.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AthleteDetailComponent,
    resolve: {
      athlete: AthleteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.athlete.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AthleteUpdateComponent,
    resolve: {
      athlete: AthleteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.athlete.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AthleteUpdateComponent,
    resolve: {
      athlete: AthleteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.athlete.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
