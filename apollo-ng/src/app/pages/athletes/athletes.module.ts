import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AthletesRoutingModule} from './athletes-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {AthletesListComponent} from './list/athletes-list.component';
import {AthletesEditComponent} from './edit/athletes-edit.component';
import {AthletesComponent} from './athletes.component';
import {PrimeNgComponentsModule} from '../../shared/prime-ng-components.module';
import {MessageService} from '../../modules/core/services/message.service';
import {WorkoutService} from '../../services/rest/workout.service';
import {ActivityService} from '../../services/rest/activity.service';
import {AthleteService} from '../../services/rest/athlete.service';

@NgModule({
    declarations: [
        AthletesComponent,
        AthletesListComponent,
        AthletesEditComponent,
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        AthletesRoutingModule,
        TranslateModule,
        PrimeNgComponentsModule,
    ],
    providers: [
        AthleteService,
        MessageService
    ],
    exports: [
        AthletesComponent,
        AthletesListComponent,
        AthletesEditComponent,
    ]
})
export class AthletesModule {
}
