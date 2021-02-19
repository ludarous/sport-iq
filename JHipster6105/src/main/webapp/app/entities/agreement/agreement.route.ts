import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAgreement, Agreement } from 'app/shared/model/agreement.model';
import { AgreementService } from './agreement.service';
import { AgreementComponent } from './agreement.component';
import { AgreementDetailComponent } from './agreement-detail.component';
import { AgreementUpdateComponent } from './agreement-update.component';

@Injectable({ providedIn: 'root' })
export class AgreementResolve implements Resolve<IAgreement> {
  constructor(private service: AgreementService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAgreement> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((agreement: HttpResponse<Agreement>) => {
          if (agreement.body) {
            return of(agreement.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Agreement());
  }
}

export const agreementRoute: Routes = [
  {
    path: '',
    component: AgreementComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'Agreements',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AgreementDetailComponent,
    resolve: {
      agreement: AgreementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Agreements',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AgreementUpdateComponent,
    resolve: {
      agreement: AgreementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Agreements',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AgreementUpdateComponent,
    resolve: {
      agreement: AgreementResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Agreements',
    },
    canActivate: [UserRouteAccessService],
  },
];
