import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ActivityCategoriesRoutingModule } from './activity-categories-routing.module';
import {MaterialComponentsModule} from '../../../shared/material-components.module';
import {PrimeNgComponentsModule} from '../../../shared/primeng-components.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {SharedModule} from '../../../shared/shared.module';
import {NgMultiSelectDropDownModule} from 'ng-multiselect-dropdown';
import {ActivityCategoriesListComponent} from './list/activity-categories-list.component';
import {ActivityCategoriesEditComponent} from './edit/activity-categories-edit.component';
import {ActivityCategoriesComponent} from './activity-categories.component';

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
    MaterialComponentsModule,
    PrimeNgComponentsModule,
    TranslateModule,
    SharedModule,
    NgMultiSelectDropDownModule
  ],
  exports: [
    ActivityCategoriesComponent,
    ActivityCategoriesListComponent,
    ActivityCategoriesEditComponent,
  ]
})
export class ActivityCategoriesModule { }
