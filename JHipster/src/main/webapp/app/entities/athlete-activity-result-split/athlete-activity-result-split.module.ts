import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared';
import {
    AthleteActivityResultSplitComponent,
    AthleteActivityResultSplitDetailComponent,
    AthleteActivityResultSplitUpdateComponent,
    AthleteActivityResultSplitDeletePopupComponent,
    AthleteActivityResultSplitDeleteDialogComponent,
    athleteActivityResultSplitRoute,
    athleteActivityResultSplitPopupRoute
} from './';

const ENTITY_STATES = [...athleteActivityResultSplitRoute, ...athleteActivityResultSplitPopupRoute];

@NgModule({
    imports: [SportiqSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AthleteActivityResultSplitComponent,
        AthleteActivityResultSplitDetailComponent,
        AthleteActivityResultSplitUpdateComponent,
        AthleteActivityResultSplitDeleteDialogComponent,
        AthleteActivityResultSplitDeletePopupComponent
    ],
    entryComponents: [
        AthleteActivityResultSplitComponent,
        AthleteActivityResultSplitUpdateComponent,
        AthleteActivityResultSplitDeleteDialogComponent,
        AthleteActivityResultSplitDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SportiqAthleteActivityResultSplitModule {}
