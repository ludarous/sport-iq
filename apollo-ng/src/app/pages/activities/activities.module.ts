import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ActivitiesRoutingModule} from './activities-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {ActivitiesListComponent} from './list/activities-list.component';
import {ActivitiesEditComponent} from './edit/activities-edit.component';
import {ActivitiesComponent} from './activities.component';
import {PrimeNgComponentsModule} from '../../shared/prime-ng-components.module';
import {MessageService} from '../../modules/core/services/message.service';
import {ActivityService} from '../../services/rest/activity.service';
import {RxReactiveFormsModule} from '@rxweb/reactive-form-validators';

@NgModule({
    declarations: [
        ActivitiesComponent,
        ActivitiesListComponent,
        ActivitiesEditComponent,
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        RxReactiveFormsModule,
        ActivitiesRoutingModule,
        TranslateModule,
        PrimeNgComponentsModule,
    ],
    providers: [
        ActivityService,
        MessageService
    ],
    exports: [
        ActivitiesComponent,
        ActivitiesListComponent,
        ActivitiesEditComponent,
    ]
})
export class ActivitiesModule {
}
