import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {UnitsRoutingModule} from './units-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {UnitsListComponent} from './list/units-list.component';
import {UnitsEditComponent} from './edit/units-edit.component';
import {UnitsComponent} from './units.component';
import {PrimeNgComponentsModule} from '../../../shared/prime-ng-components.module';
import {ToastService} from '../../../../modules/core/services/message.service';
import {UnitService} from '../../../services/rest/unit.service';

@NgModule({
    declarations: [
        UnitsComponent,
        UnitsListComponent,
        UnitsEditComponent,
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        UnitsRoutingModule,
        TranslateModule,
        PrimeNgComponentsModule,
    ],
    providers: [
        UnitService,
    ],
    exports: [
        UnitsComponent,
        UnitsListComponent,
        UnitsEditComponent,
    ]
})
export class UnitsModule {
}
