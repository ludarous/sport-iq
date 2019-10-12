import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared';
import {
    WorkoutComponent,
    WorkoutDetailComponent,
    WorkoutUpdateComponent,
    WorkoutDeletePopupComponent,
    WorkoutDeleteDialogComponent,
    workoutRoute,
    workoutPopupRoute
} from './';

const ENTITY_STATES = [...workoutRoute, ...workoutPopupRoute];

@NgModule({
    imports: [SportiqSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WorkoutComponent,
        WorkoutDetailComponent,
        WorkoutUpdateComponent,
        WorkoutDeleteDialogComponent,
        WorkoutDeletePopupComponent
    ],
    entryComponents: [WorkoutComponent, WorkoutUpdateComponent, WorkoutDeleteDialogComponent, WorkoutDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SportiqWorkoutModule {}
