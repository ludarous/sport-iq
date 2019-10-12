import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared';
import {
    AthleteEventComponent,
    AthleteEventDetailComponent,
    AthleteEventUpdateComponent,
    AthleteEventDeletePopupComponent,
    AthleteEventDeleteDialogComponent,
    athleteEventRoute,
    athleteEventPopupRoute
} from './';

const ENTITY_STATES = [...athleteEventRoute, ...athleteEventPopupRoute];

@NgModule({
    imports: [SportiqSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AthleteEventComponent,
        AthleteEventDetailComponent,
        AthleteEventUpdateComponent,
        AthleteEventDeleteDialogComponent,
        AthleteEventDeletePopupComponent
    ],
    entryComponents: [
        AthleteEventComponent,
        AthleteEventUpdateComponent,
        AthleteEventDeleteDialogComponent,
        AthleteEventDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SportiqAthleteEventModule {}
