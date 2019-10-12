import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared';
import {
    ActivityResultSplitComponent,
    ActivityResultSplitDetailComponent,
    ActivityResultSplitUpdateComponent,
    ActivityResultSplitDeletePopupComponent,
    ActivityResultSplitDeleteDialogComponent,
    activityResultSplitRoute,
    activityResultSplitPopupRoute
} from './';

const ENTITY_STATES = [...activityResultSplitRoute, ...activityResultSplitPopupRoute];

@NgModule({
    imports: [SportiqSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ActivityResultSplitComponent,
        ActivityResultSplitDetailComponent,
        ActivityResultSplitUpdateComponent,
        ActivityResultSplitDeleteDialogComponent,
        ActivityResultSplitDeletePopupComponent
    ],
    entryComponents: [
        ActivityResultSplitComponent,
        ActivityResultSplitUpdateComponent,
        ActivityResultSplitDeleteDialogComponent,
        ActivityResultSplitDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SportiqActivityResultSplitModule {}
