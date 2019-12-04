import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ActivityCategoriesRoutingModule} from './activity-categories-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {ActivityCategoriesListComponent} from './list/activity-categories-list.component';
import {ActivityCategoriesEditComponent} from './edit/activity-categories-edit.component';
import {ActivityCategoriesComponent} from './activity-categories.component';
import {PrimeNgComponentsModule} from '../../shared/prime-ng-components.module';
import {ActivityCategoryService} from '../../services/rest/activity-category.service';
import {ToastService} from '../../modules/core/services/message.service';

@NgModule({
    declarations: [
        ActivityCategoriesComponent,
        ActivityCategoriesListComponent,
        ActivityCategoriesEditComponent,
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        ActivityCategoriesRoutingModule,
        TranslateModule,
        PrimeNgComponentsModule,
    ],
    providers: [
        ActivityCategoryService
    ],
    exports: [
        ActivityCategoriesComponent,
        ActivityCategoriesListComponent,
        ActivityCategoriesEditComponent,
    ]
})
export class ActivityCategoriesModule {
}
