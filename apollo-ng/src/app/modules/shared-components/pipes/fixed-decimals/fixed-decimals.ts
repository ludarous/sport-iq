import {Pipe, PipeTransform} from '@angular/core';
import {EnumTranslatorService} from '../../services/enum-translator.service';
import {NumberInputUtils} from '../../../core/utils/number-input-utils';

@Pipe({name: 'fixedDecimals'})
export class FixedDecimalsPipe implements PipeTransform {

  constructor() {

  }

  transform(value: any, decimals: number = 2): any {
    return NumberInputUtils.valueToFixedDecimals(value, decimals);
  }

}
