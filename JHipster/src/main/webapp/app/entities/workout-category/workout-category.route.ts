import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { WorkoutCategory } from 'app/shared/model/workout-category.model';
import { WorkoutCategoryService } from './workout-category.service';
import { WorkoutCategoryComponent } from './workout-category.component';
import { WorkoutCategoryDetailComponent } from './workout-category-detail.component';
import { WorkoutCategoryUpdateComponent } from './workout-category-update.component';
import { IWorkoutCategory } from 'app/shared/model/workout-category.model';

@Injectable({ providedIn: 'root' })
export class WorkoutCategoryResolve implements Resolve<IWorkoutCategory> {
  constructor(private service: WorkoutCategoryService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWorkoutCategory> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(map((workoutCategory: HttpResponse<WorkoutCategory>) => workoutCategory.body));
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
