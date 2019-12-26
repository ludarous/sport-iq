import {FormGroup} from '@angular/forms';
import {LogicUtils} from './logic.utils';

export class NumberInputUtils {

  public static readonly DEFAULT_MAX = 99999999.9999;
  public static readonly DEFAULT_MIN = -99999999.9999;
  public static readonly EMPTY_VALUE_NUMBER = 0;

  // replace empty input for zero value
  static fixValueOnInputFocusOut($target: JQuery<EventTarget>,
                                 formGroup: FormGroup,
                                 formControlName: string,
                                 fixedDecimals = true,
                                 fixNull = true,
                                 minValue = NumberInputUtils.EMPTY_VALUE_NUMBER) {
    // set zero on empty input
    if (LogicUtils.nullOrUndefinedOrEmpty($target.val()) && fixNull) {
      const finalValue = fixedDecimals ? this.valueToFixedDecimals(minValue) : minValue;
      this.patchControl(finalValue, formGroup, formControlName);
    } else {
      const finalValue = fixedDecimals ? this.valueToFixedDecimals($target.val(), 2, minValue) : $target.val();
      this.patchControl(finalValue, formGroup, formControlName);
    }

  }

  // replace empty input for zero value
  static fixValueOnInputFocusOutForModel($target: JQuery<EventTarget>,
                                         fixedDecimals = true,
                                         fixNull = true,
                                         fixValue = NumberInputUtils.EMPTY_VALUE_NUMBER): string {
    let fixedValue = '';
    const value = <string>$target.val();
    if (LogicUtils.nullOrUndefinedOrEmpty($target.val() && fixNull)) {
      fixedValue = fixedDecimals ? this.valueToFixedDecimals(fixValue) : fixValue.toString();
    } else {
      fixedValue = fixedDecimals ? this.valueToFixedDecimals(value) : value;
    }

    return fixedValue;
  }

  // remove extra spaces
  static fixIntegerOnInput($target, formGroup, formControlName): string {

    const patchValue = this.fixInteger($target);
    this.patchControl(patchValue, formGroup, formControlName);
    return patchValue;

  }

  // remove extra spaces, commas and/or dots
  static fixDecimalOnInput($target, formGroup, formControlName): string {

    const patchValue = this.fixDecimal($target);
    this.patchControl(patchValue, formGroup, formControlName);
    return patchValue;
  }

  static fixInteger($target: JQuery<EventTarget>): string {
    const value = $target ? $target.val() : null;

    if (!LogicUtils.nullOrUndefinedOrEmpty(value)) {
      const originalValue = value;
      let patchValue = (<string>value);

      // remove spaces
      patchValue = patchValue.replace(/\s/g, '');
      return patchValue;
    }

    return <string>$target.val();
  }

  static fixDecimal($target: JQuery<EventTarget>): string {
    const value = $target ? $target.val() : null;

    if (!LogicUtils.nullOrUndefinedOrEmpty(value)) {
      const originalValue = value;
      let patchValue = (<string>value);

      // remove spaces
      patchValue = patchValue.replace(/\s/g, '');

      // remove extra dots/commas
      const indexOfComma = patchValue.indexOf(',');
      const indexOfPoint = patchValue.indexOf('.');
      // detect both point and comma in input - try to fix inputs such as "12,222,333.12" and "12.223,25"
      if (indexOfComma > -1 && indexOfPoint > -1) {
        if (indexOfComma > indexOfPoint) {
          // remove all points
          patchValue = patchValue.replace(/\./g, '');
        } else {
          // remove all commas
          patchValue = patchValue.replace(/,/g, '');
        }
      }

      return patchValue.replace('.', ',');
    }

    return <string>$target.val();
  }

  static patchControl(value: any, formGroup: FormGroup, formControlName: string) {
    formGroup.get(formControlName).patchValue(value);
    formGroup.get(formControlName).updateValueAndValidity();
  }

  static inputIsDecimal(value: string): boolean {
    if (!value) {
      return true;
    }
    const reg = new RegExp('^[-+]?[\\d.,\\s]*$');
    const result = reg.test(value);
    if (result) {
      return true;
    }
    return result;
  }

  static inputIsInteger(value: string, min = NumberInputUtils.DEFAULT_MIN, max = NumberInputUtils.DEFAULT_MAX): boolean {
    if (!value) {
      return true;
    }
    const reg = new RegExp('^[-+]?[\\d\\s]*$');
    const result = reg.test(value);
    if (result) {
      return this.isInLimit(value, min, max);
    }
    return result;
  }

  static isInLimit(value: string, min = NumberInputUtils.DEFAULT_MIN, max = NumberInputUtils.DEFAULT_MAX): boolean {
    if (!LogicUtils.nullOrUndefinedOrEmpty(value)) {

      if (value === '-' || value === '+') {
        return true;
      }

      const resultValue = this.toNumber(value);
      return resultValue >= min && resultValue <= max;
    }
    return true;
  }

  static valueToFixedDecimals(value: any, decimals = 2, minValue = NumberInputUtils.DEFAULT_MIN): string {
    if (!LogicUtils.nullOrUndefinedOrEmpty(value)) {
      if (NumberInputUtils.inputIsDecimal(value)) {
        let numValue = this.toNumber(value);
        if (numValue < minValue) {
          numValue = minValue;
        }
        return numValue.toFixed(decimals).replace('.', ',');
      }
    }
    return value;
  }

  static toNumber(value: any): number {
    if (!LogicUtils.nullOrUndefinedOrEmpty(value) && typeof (value) !== 'number') {
      value = value.replace(',', '.');
      const numberValue = Number(value);
      if (Number.isNaN(numberValue)) {
        return 0;
      } else {
        return numberValue;
      }
    }
    return value;
  }

  static parseNumberFromStringOrReturnZero(input: string | number): number {
    if (typeof input === 'number') {
      return input;
    }
    if (!input || 0 === input.length) {
      return 0;
    }
    let inputConverted = input;
    // convert potential colon
    if (inputConverted.indexOf(',') >= 0) {
      inputConverted = inputConverted.replace(/,/, '.');
    }
    const result = Number(inputConverted);
    return isNaN(result) ? NumberInputUtils.EMPTY_VALUE_NUMBER : result;
  }

}
