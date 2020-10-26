import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AthleteRoutingModule } from './athlete-routing.module';
import { ProfileComponent } from './profile/profile.component';
import { SportService } from '../../services/rest/sport.service';
import { TranslateModule } from '@ngx-translate/core';
import { ReactiveFormsModule } from '@angular/forms';
import { VcSharedComponentsModule } from '../../../modules/shared-components/vc-shared-components.module';
import { RxReactiveFormsModule } from '@rxweb/reactive-form-validators';
import { PrimeNgComponentsModule } from '../../shared/prime-ng-components.module';
import { AthleteService } from '../../services/rest/athlete.service';


@NgModule({
  declarations: [
      ProfileComponent
  ],
    imports: [
        CommonModule,
        AthleteRoutingModule,
        VcSharedComponentsModule,
        ReactiveFormsModule,
        RxReactiveFormsModule,
        PrimeNgComponentsModule
    ],
    providers: [
        SportService,
        AthleteService,
    ]
})
export class AthleteModule { }
