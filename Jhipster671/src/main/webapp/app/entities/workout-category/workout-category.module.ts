import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared/shared.module';
import { WorkoutCategoryComponent } from './workout-category.component';
import { WorkoutCategoryDetailComponent } from './workout-category-detail.component';
import { WorkoutCategoryUpdateComponent } from './workout-category-update.component';
import { WorkoutCategoryDeleteDialogComponent } from './workout-category-delete-dialog.component';
import { workoutCategoryRoute } from './workout-category.route';

@NgModule({
  imports: [SportiqSharedModule, RouterModule.forChild(workoutCategoryRoute)],
  declarations: [
    WorkoutCategoryComponent,
    WorkoutCategoryDetailComponent,
    WorkoutCategoryUpdateComponent,
    WorkoutCategoryDeleteDialogComponent
  ],
  entryComponents: [WorkoutCategoryDeleteDialogComponent]
})
export class SportiqWorkoutCategoryModule {}
