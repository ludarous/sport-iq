import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ActivitiesRoutingModule } from './activities-routing.module';
import { ActivitiesComponent } from './activities.component';
import {MaterialComponentsModule} from '../../../shared/material-components.module';
import {PrimeNgComponentsModule} from '../../../shared/primeng-components.module';
import {ActivitiesListComponent} from './list/activities-list.component';
import {ActivitiesEditComponent} from './edit/activities-edit.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {SharedModule} from '../../../shared/shared.module';
import {NgMultiSelectDropDownModule} from 'ng-multiselect-dropdown'
import {EnumTranslationsModule} from '../../../shared/pipes/enum-translator/enum-translations.module';
import {SharedComponentsModule} from '../../../shared/components/shared-components.module';

@NgModule({
  declarations: [
    ActivitiesComponent,
    ActivitiesListComponent,
    ActivitiesEditComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ActivitiesRoutingModule,
    MaterialComponentsModule,
    PrimeNgComponentsModule,
    TranslateModule,
    SharedModule,
    NgMultiSelectDropDownModule,
    EnumTranslationsModule,
    SharedComponentsModule
  ],
  exports: [
    ActivitiesComponent,
    ActivitiesListComponent,
    ActivitiesEditComponent,
  ]
})
export class ActivitiesModule { }
