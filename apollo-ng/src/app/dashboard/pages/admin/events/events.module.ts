import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {EventsRoutingModule} from './events-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {EventsListComponent} from './list/events-list.component';
import {EventsEditComponent} from './edit/events-edit.component';
import {EventsComponent} from './events.component';
import {PrimeNgComponentsModule} from '../../../shared/prime-ng-components.module';
import {ToastService} from '../../../../modules/core/services/message.service';
import {EventService} from '../../../services/rest/event.service';
import {WorkoutService} from '../../../services/rest/workout.service';
import {Athlete} from '../../../entities/model/athlete.model';
import {AthleteService} from '../../../services/rest/athlete.service';
import { EventsResultsComponent } from './results/events-results.component';
import {AthleteEventService} from '../../../services/rest/athlete-event.service';
import {AthleteEventComponent} from './results/athlete-event/athlete-event.component';
import {AthleteWorkoutComponent} from './results/athlete-workout/athlete-workout.component';
import {AthleteWorkoutService} from '../../../services/rest/athlete-workout.service';
import {AthleteActivityComponent} from './results/athlete-activity/athlete-activity.component';
import {AthleteActivityService} from '../../../services/rest/athlete-activity.service';
import {AthleteActivityResultComponent} from './results/athlete-activity-result/athlete-activity-result.component';
import {AthleteActivityResultSplitComponent} from './results/athlete-activity-result-split/athlete-activity-result-split.component';
import {EventResultsService} from './results/events-results.service';
import { EventLocationService } from '../../../services/rest/event-location.service';

@NgModule({
    declarations: [
        EventsComponent,
        EventsListComponent,
        EventsEditComponent,
        EventsResultsComponent,
        AthleteEventComponent,
        AthleteWorkoutComponent,
        AthleteActivityComponent,
        AthleteActivityResultComponent,
        AthleteActivityResultSplitComponent,
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        EventsRoutingModule,
        TranslateModule,
        PrimeNgComponentsModule,
    ],
    providers: [
        EventService,
        WorkoutService,
        AthleteService,
        AthleteEventService,
        AthleteWorkoutService,
        AthleteActivityService,
        EventResultsService,
        EventLocationService,
    ],
    exports: [
        EventsComponent,
        EventsListComponent,
        EventsEditComponent,
        EventsResultsComponent,
        AthleteEventComponent,
        AthleteWorkoutComponent
    ]
})
export class EventsModule {
}
