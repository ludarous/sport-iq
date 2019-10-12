import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared';
import {
    AthleteWorkoutComponent,
    AthleteWorkoutDetailComponent,
    AthleteWorkoutUpdateComponent,
    AthleteWorkoutDeletePopupComponent,
    AthleteWorkoutDeleteDialogComponent,
    athleteWorkoutRoute,
    athleteWorkoutPopupRoute
} from './';

const ENTITY_STATES = [...athleteWorkoutRoute, ...athleteWorkoutPopupRoute];

@NgModule({
    imports: [SportiqSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AthleteWorkoutComponent,
        AthleteWorkoutDetailComponent,
        AthleteWorkoutUpdateComponent,
        AthleteWorkoutDeleteDialogComponent,
        AthleteWorkoutDeletePopupComponent
    ],
    entryComponents: [
        AthleteWorkoutComponent,
        AthleteWorkoutUpdateComponent,
        AthleteWorkoutDeleteDialogComponent,
        AthleteWorkoutDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SportiqAthleteWorkoutModule {}
