import {ModuleWithProviders, NgModule} from '@angular/core';
import {DatexPipe} from './pipes/datex/datex.pipe';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {EnumTranslationsModule} from './enum-translations.module';
import {EnumTranslatorService} from './services/enum-translator.service';
import {ModuleConfig} from '../config';
import {CommonModule} from '@angular/common';
import {CallbackPipe} from './pipes/callback/callback.pipe';
import {NbspPipe} from './pipes/nbsp/nbsp';
import {FixedDecimalsPipe} from './pipes/fixed-decimals/fixed-decimals';
import {TranslateModule} from '@ngx-translate/core';

const COMPONENTS = [

  // pipes
  DatexPipe,
  CallbackPipe,
  NbspPipe,
  FixedDecimalsPipe,
];


@NgModule({
  declarations: [
    COMPONENTS,
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule,
    EnumTranslationsModule,
  ],
  entryComponents: [
  ],
  exports: [
    COMPONENTS,

    TranslateModule,
    EnumTranslationsModule,
  ]
})
export class VcSharedComponentsModule {
  static forRoot(enumTranslatorService: any, config: ModuleConfig): ModuleWithProviders {
    return {
      ngModule: VcSharedComponentsModule,
      providers: [
        {provide: EnumTranslatorService, useClass: enumTranslatorService},
        {provide: ModuleConfig, useValue: config}
      ]};
  }
}
