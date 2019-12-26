import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ActivitiesRoutingModule} from './activities-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {ActivitiesListComponent} from './list/activities-list.component';
import {ActivitiesEditComponent} from './edit/activities-edit.component';
import {ActivitiesComponent} from './activities.component';
import {PrimeNgComponentsModule} from '../../shared/prime-ng-components.module';
import {ToastService} from '../../../modules/core/services/message.service';
import {ActivityService} from '../../services/rest/activity.service';
import {RxReactiveFormsModule} from '@rxweb/reactive-form-validators';
import {ActivityCategoryService} from '../../services/rest/activity-category.service';
import {UnitService} from '../../services/rest/unit.service';
import { ActivityResultsListComponent } from './activity-results/list/activity-results-list.component';
import {ActivityResultService} from '../../services/rest/activity-result.service';
import { ActivityResultsEditComponent } from './activity-results/edit/activity-results-edit.component';
import {DialogService} from 'primeng/api';
import {DynamicDialogComponent} from 'primeng/dynamicdialog';
import {ActivityResultSplitService} from '../../services/rest/activity-result-split.service';
import { ActivitiesPagesService } from './activities-pages.service';

@NgModule({
    declarations: [
        ActivitiesComponent,
        ActivitiesListComponent,
        ActivitiesEditComponent,
        ActivityResultsListComponent,
        ActivityResultsEditComponent,
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
        ActivityCategoryService,
        ActivitiesPagesService,
        UnitService,
        ActivityResultService,
        ActivityResultSplitService,
        DialogService
    ],
    exports: [
        ActivitiesComponent,
        ActivitiesListComponent,
        ActivitiesEditComponent,
    ],
    entryComponents: [
        ActivityResultsEditComponent,
    ]
})
export class ActivitiesModule {
}
