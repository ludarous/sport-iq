import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CommonModule} from '@angular/common';
import {BrowserModule} from '@angular/platform-browser';
import {TranslateModule} from '@ngx-translate/core';
import {VcSharedComponentsModule} from '../shared-components/vc-shared-components.module';

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule,
    TranslateModule,
    VcSharedComponentsModule,
  ],
  providers: [

  ],
  declarations: [

  ],
  exports: [

  ]
})

export class CoreModule {
}
