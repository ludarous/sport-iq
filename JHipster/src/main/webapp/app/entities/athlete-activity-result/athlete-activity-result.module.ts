import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared';
import {
    AthleteActivityResultComponent,
    AthleteActivityResultDetailComponent,
    AthleteActivityResultUpdateComponent,
    AthleteActivityResultDeletePopupComponent,
    AthleteActivityResultDeleteDialogComponent,
    athleteActivityResultRoute,
    athleteActivityResultPopupRoute
} from './';

const ENTITY_STATES = [...athleteActivityResultRoute, ...athleteActivityResultPopupRoute];

@NgModule({
    imports: [SportiqSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AthleteActivityResultComponent,
        AthleteActivityResultDetailComponent,
        AthleteActivityResultUpdateComponent,
        AthleteActivityResultDeleteDialogComponent,
        AthleteActivityResultDeletePopupComponent
    ],
    entryComponents: [
        AthleteActivityResultComponent,
        AthleteActivityResultUpdateComponent,
        AthleteActivityResultDeleteDialogComponent,
        AthleteActivityResultDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SportiqAthleteActivityResultModule {}
