import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared';
import {
    AthleteComponent,
    AthleteDetailComponent,
    AthleteUpdateComponent,
    AthleteDeletePopupComponent,
    AthleteDeleteDialogComponent,
    athleteRoute,
    athletePopupRoute
} from './';

const ENTITY_STATES = [...athleteRoute, ...athletePopupRoute];

@NgModule({
    imports: [SportiqSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AthleteComponent,
        AthleteDetailComponent,
        AthleteUpdateComponent,
        AthleteDeleteDialogComponent,
        AthleteDeletePopupComponent
    ],
    entryComponents: [AthleteComponent, AthleteUpdateComponent, AthleteDeleteDialogComponent, AthleteDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SportiqAthleteModule {}
