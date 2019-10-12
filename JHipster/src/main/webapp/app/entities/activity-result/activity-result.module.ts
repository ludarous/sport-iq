import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared';
import {
    ActivityResultComponent,
    ActivityResultDetailComponent,
    ActivityResultUpdateComponent,
    ActivityResultDeletePopupComponent,
    ActivityResultDeleteDialogComponent,
    activityResultRoute,
    activityResultPopupRoute
} from './';

const ENTITY_STATES = [...activityResultRoute, ...activityResultPopupRoute];

@NgModule({
    imports: [SportiqSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ActivityResultComponent,
        ActivityResultDetailComponent,
        ActivityResultUpdateComponent,
        ActivityResultDeleteDialogComponent,
        ActivityResultDeletePopupComponent
    ],
    entryComponents: [
        ActivityResultComponent,
        ActivityResultUpdateComponent,
        ActivityResultDeleteDialogComponent,
        ActivityResultDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SportiqActivityResultModule {}
