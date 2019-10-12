import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportiqSharedModule } from 'app/shared';
import {
    SportComponent,
    SportDetailComponent,
    SportUpdateComponent,
    SportDeletePopupComponent,
    SportDeleteDialogComponent,
    sportRoute,
    sportPopupRoute
} from './';

const ENTITY_STATES = [...sportRoute, ...sportPopupRoute];

@NgModule({
    imports: [SportiqSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SportComponent, SportDetailComponent, SportUpdateComponent, SportDeleteDialogComponent, SportDeletePopupComponent],
    entryComponents: [SportComponent, SportUpdateComponent, SportDeleteDialogComponent, SportDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SportiqSportModule {}
