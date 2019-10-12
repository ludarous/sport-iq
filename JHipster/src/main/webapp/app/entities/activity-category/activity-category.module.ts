import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared';
import {
    ActivityCategoryComponent,
    ActivityCategoryDetailComponent,
    ActivityCategoryUpdateComponent,
    ActivityCategoryDeletePopupComponent,
    ActivityCategoryDeleteDialogComponent,
    activityCategoryRoute,
    activityCategoryPopupRoute
} from './';

const ENTITY_STATES = [...activityCategoryRoute, ...activityCategoryPopupRoute];

@NgModule({
    imports: [SportiqSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ActivityCategoryComponent,
        ActivityCategoryDetailComponent,
        ActivityCategoryUpdateComponent,
        ActivityCategoryDeleteDialogComponent,
        ActivityCategoryDeletePopupComponent
    ],
    entryComponents: [
        ActivityCategoryComponent,
        ActivityCategoryUpdateComponent,
        ActivityCategoryDeleteDialogComponent,
        ActivityCategoryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SportiqActivityCategoryModule {}
