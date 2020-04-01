import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {WorkoutsRoutingModule} from './workouts-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {WorkoutsListComponent} from './list/workouts-list.component';
import {WorkoutsEditComponent} from './edit/workouts-edit.component';
import {WorkoutsComponent} from './workouts.component';
import {PrimeNgComponentsModule} from '../../../shared/prime-ng-components.module';
import {ToastService} from '../../../../modules/core/services/message.service';
import {WorkoutService} from '../../../services/rest/workout.service';
import {ActivityService} from '../../../services/rest/activity.service';

@NgModule({
    declarations: [
        WorkoutsComponent,
        WorkoutsListComponent,
        WorkoutsEditComponent,
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        WorkoutsRoutingModule,
        TranslateModule,
        PrimeNgComponentsModule,
    ],
    providers: [
        WorkoutService,
        ActivityService,
    ],
    exports: [
        WorkoutsComponent,
        WorkoutsListComponent,
        WorkoutsEditComponent,
    ]
})
export class WorkoutsModule {
}
