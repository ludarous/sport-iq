import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILegalRepresentative, LegalRepresentative } from 'app/shared/model/legal-representative.model';
import { LegalRepresentativeService } from './legal-representative.service';
import { LegalRepresentativeComponent } from './legal-representative.component';
import { LegalRepresentativeDetailComponent } from './legal-representative-detail.component';
import { LegalRepresentativeUpdateComponent } from './legal-representative-update.component';

@Injectable({ providedIn: 'root' })
export class LegalRepresentativeResolve implements Resolve<ILegalRepresentative> {
  constructor(private service: LegalRepresentativeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILegalRepresentative> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((legalRepresentative: HttpResponse<LegalRepresentative>) => {
          if (legalRepresentative.body) {
            return of(legalRepresentative.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LegalRepresentative());
  }
}

export const legalRepresentativeRoute: Routes = [
  {
    path: '',
    component: LegalRepresentativeComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'LegalRepresentatives',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: LegalRepresentativeDetailComponent,
    resolve: {
      legalRepresentative: LegalRepresentativeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'LegalRepresentatives',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: LegalRepresentativeUpdateComponent,
    resolve: {
      legalRepresentative: LegalRepresentativeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'LegalRepresentatives',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: LegalRepresentativeUpdateComponent,
    resolve: {
      legalRepresentative: LegalRepresentativeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'LegalRepresentatives',
    },
    canActivate: [UserRouteAccessService],
  },
];
