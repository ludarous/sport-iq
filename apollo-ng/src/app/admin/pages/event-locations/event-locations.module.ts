import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {EventLocationsRoutingModule} from './event-locations-routing.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {TranslateModule} from '@ngx-translate/core';
import {EventLocationsListComponent} from './list/event-locations-list.component';
import {EventLocationsEditComponent} from './edit/event-locations-edit.component';
import {EventLocationsComponent} from './event-locations.component';
import {PrimeNgComponentsModule} from '../../shared/prime-ng-components.module';
import { EventLocationService } from '../../services/rest/event-location.service';

@NgModule({
    declarations: [
        EventLocationsComponent,
        EventLocationsListComponent,
        EventLocationsEditComponent,
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        EventLocationsRoutingModule,
        TranslateModule,
        PrimeNgComponentsModule,
    ],
    providers: [
        EventLocationService,
    ],
    exports: [
        EventLocationsComponent,
        EventLocationsListComponent,
        EventLocationsEditComponent,
    ]
})
export class EventLocationsModule {
}
