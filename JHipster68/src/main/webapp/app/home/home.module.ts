import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipster68SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';

@NgModule({
  imports: [JHipster68SharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class JHipster68HomeModule {}
