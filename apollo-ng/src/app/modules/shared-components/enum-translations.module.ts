import {ModuleWithProviders, NgModule} from '@angular/core';
import {EnumTranslatorPipe} from './pipes/enum-translator/enum-translator';
import {EnumTranslatorService} from './services/enum-translator.service';

@NgModule({
  imports: [
  ],
  declarations: [
    EnumTranslatorPipe,
  ],
  exports: [
    EnumTranslatorPipe,
  ]
})
export class EnumTranslationsModule {
  static forRoot(enumTranslatorService: any): ModuleWithProviders {
    return {
      ngModule: EnumTranslationsModule,
      providers: [
        {provide: EnumTranslatorService, useClass: enumTranslatorService}

      ]};
  }
}
