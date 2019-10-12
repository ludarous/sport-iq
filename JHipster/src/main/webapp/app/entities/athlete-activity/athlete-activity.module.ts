import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared';
import {
    AthleteActivityComponent,
    AthleteActivityDetailComponent,
    AthleteActivityUpdateComponent,
    AthleteActivityDeletePopupComponent,
    AthleteActivityDeleteDialogComponent,
    athleteActivityRoute,
    athleteActivityPopupRoute
} from './';

const ENTITY_STATES = [...athleteActivityRoute, ...athleteActivityPopupRoute];

@NgModule({
    imports: [SportiqSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AthleteActivityComponent,
        AthleteActivityDetailComponent,
        AthleteActivityUpdateComponent,
        AthleteActivityDeleteDialogComponent,
        AthleteActivityDeletePopupComponent
    ],
    entryComponents: [
        AthleteActivityComponent,
        AthleteActivityUpdateComponent,
        AthleteActivityDeleteDialogComponent,
        AthleteActivityDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SportiqAthleteActivityModule {}
