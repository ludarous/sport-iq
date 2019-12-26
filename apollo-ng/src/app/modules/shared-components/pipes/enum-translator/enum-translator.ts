import {Pipe, PipeTransform} from '@angular/core';
import {EnumTranslatorService} from '../../services/enum-translator.service';

@Pipe({name: 'enumTranslator'})
export class EnumTranslatorPipe implements PipeTransform {

  constructor(private enumTranslatorService: EnumTranslatorService) {

  }

  transform(value: any): any {
    return this.enumTranslatorService.translate(value);
  }

}
