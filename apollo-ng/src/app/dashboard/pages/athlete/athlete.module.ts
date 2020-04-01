import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AthleteRoutingModule } from './athlete-routing.module';
import { ProfileComponent } from './profile/profile.component';


@NgModule({
  declarations: [
      ProfileComponent
  ],
  imports: [
    CommonModule,
    AthleteRoutingModule
  ]
})
export class AthleteModule { }
