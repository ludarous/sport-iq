import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWorkoutCategory, WorkoutCategory } from 'app/shared/model/workout-category.model';
import { WorkoutCategoryService } from './workout-category.service';
import { WorkoutCategoryComponent } from './workout-category.component';
import { WorkoutCategoryDetailComponent } from './workout-category-detail.component';
import { WorkoutCategoryUpdateComponent } from './workout-category-update.component';

@Injectable({ providedIn: 'root' })
export class WorkoutCategoryResolve implements Resolve<IWorkoutCategory> {
  constructor(private service: WorkoutCategoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkoutCategory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((workoutCategory: HttpResponse<WorkoutCategory>) => {
          if (workoutCategory.body) {
            return of(workoutCategory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new WorkoutCategory());
  }
}

export const workoutCategoryRoute: Routes = [
  {
    path: '',
    component: WorkoutCategoryComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'sportiqApp.workoutCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: WorkoutCategoryDetailComponent,
    resolve: {
      workoutCategory: WorkoutCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.workoutCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: WorkoutCategoryUpdateComponent,
    resolve: {
      workoutCategory: WorkoutCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.workoutCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: WorkoutCategoryUpdateComponent,
    resolve: {
      workoutCategory: WorkoutCategoryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sportiqApp.workoutCategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
