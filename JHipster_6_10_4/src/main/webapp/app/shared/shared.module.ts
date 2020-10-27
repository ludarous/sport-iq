import { NgModule } from '@angular/core';
import { SportIqSharedLibsModule } from './shared-libs.module';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';

@NgModule({
  imports: [SportIqSharedLibsModule],
  declarations: [AlertComponent, AlertErrorComponent, HasAnyAuthorityDirective],
  exports: [SportIqSharedLibsModule, AlertComponent, AlertErrorComponent, HasAnyAuthorityDirective],
})
export class SportIqSharedModule {}
