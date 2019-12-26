import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AthletesRoutingModule} from './athletes-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {AthletesListComponent} from './list/athletes-list.component';
import {AthletesEditComponent} from './edit/athletes-edit.component';
import {AthletesComponent} from './athletes.component';
import {PrimeNgComponentsModule} from '../../shared/prime-ng-components.module';
import {AthleteService} from '../../services/rest/athlete.service';
import {AthletesCardComponent} from './card/athletes-card.component';
import { AthleteEventsSummaryComponent } from './card/athlete-events-summary/athlete-events-summary.component';
import {AthleteEventService} from '../../services/rest/athlete-event.service';
import { AthleteWorkoutSummaryComponent } from './card/athlete-workout-summary/athlete-workout-summary.component';
import { AthleteActivitySummaryComponent } from './card/athlete-activity-summary/athlete-activity-summary.component';
import { AthleteActivityResultSummaryComponent } from './card/athlete-activity-result-summary/athlete-activity-result-summary.component';
import {NgxChartsModule} from '@swimlane/ngx-charts';
import { AthleteInfoComponent } from './card/athlete-info/athlete-info.component';
import {VcSharedComponentsModule} from '../../../modules/shared-components/vc-shared-components.module';


@NgModule({
    declarations: [
        AthletesComponent,
        AthletesListComponent,
        AthletesEditComponent,
        AthletesCardComponent,
        AthleteEventsSummaryComponent,
        AthleteWorkoutSummaryComponent,
        AthleteActivitySummaryComponent,
        AthleteActivityResultSummaryComponent,
        AthleteInfoComponent,
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        AthletesRoutingModule,
        TranslateModule,
        PrimeNgComponentsModule,
        NgxChartsModule,
        VcSharedComponentsModule,
    ],
    providers: [
        AthleteService,
        AthleteEventService,
    ],
    exports: [
        AthletesComponent,
        AthletesListComponent,
        AthletesEditComponent,
    ]
})
export class AthletesModule {
}
