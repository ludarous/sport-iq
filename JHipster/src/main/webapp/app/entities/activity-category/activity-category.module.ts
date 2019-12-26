import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared/shared.module';
import { ActivityCategoryComponent } from './activity-category.component';
import { ActivityCategoryDetailComponent } from './activity-category-detail.component';
import { ActivityCategoryUpdateComponent } from './activity-category-update.component';
import { ActivityCategoryDeleteDialogComponent } from './activity-category-delete-dialog.component';
import { activityCategoryRoute } from './activity-category.route';

@NgModule({
  imports: [SportiqSharedModule, RouterModule.forChild(activityCategoryRoute)],
  declarations: [
    ActivityCategoryComponent,
    ActivityCategoryDetailComponent,
    ActivityCategoryUpdateComponent,
    ActivityCategoryDeleteDialogComponent
  ],
  entryComponents: [ActivityCategoryDeleteDialogComponent]
})
export class SportiqActivityCategoryModule {}
