import {AbstractControl, ValidatorFn, Validators} from '@angular/forms';
import {isNull} from 'util';

export class CustomValidators {

  static equal(testedControl: AbstractControl): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      if (testedControl.value === control.value) return null;
      else {
        return {
          notEqual: true
        };
      }
    };
  }

  static ico(c: AbstractControl) {
    if (c.value) {
      const icoRegExp = new RegExp('(^$)|(^[0-9]{8}$)');
      const valid = icoRegExp.test(c.value);
      return valid ? null : {
        ico: {
          valid: false
        }
      };
    }
    return null;
  }

  static dic(c: AbstractControl) {
    if (c.value) {
      const dicRegExp = new RegExp('(^$)|(^[a-zA-Z]{2}[0-9]{8,10}$)');
      const valid = dicRegExp.test(c.value);
      return valid ? null : {
        dic: {
          valid: false
        }
      };
    }
    return null;
  }

  static phone(c: AbstractControl) {
    if (c.value) {
      const icoRegExp = new RegExp('^([0|\\+[0-9\\s+]{1,5})?([0-9\\s+]{6,13})');
      const valid = icoRegExp.test(c.value);
      return valid ? null : {
        phone: {
          valid: false
        }
      };
    }
    return null;
  }

  static visId(c: AbstractControl) {
    if (c.value) {
      const visIdRegExp = new RegExp('^[0-9]+$');
      const valid = visIdRegExp.test(c.value);
      return valid ? null : {
        visId: {
          valid: false
        }
      };
    }
    return null;
  }

  static login(c: AbstractControl) {
    if (c.value) {
      const loginRegExp = new RegExp('^[_\'.@A-Za-z0-9-]*$');
      const valid = loginRegExp.test(c.value);
      return valid ? null : {
        login: {
          valid: false
        }
      };
    }
    return null;
  }

  static loginOrEmail(c: AbstractControl) {
    if (c.value) {
      const valid = isNull(CustomValidators.login(c)) || isNull(Validators.email(c));
      return valid ? null : {
        loginOrEmail: {
          valid: false
        }
      };
    }
    return null;
  }

  static link(c: AbstractControl) {
    if (c.value) {
      const linkRegExp = new RegExp('^(http|https)://');
      const valid = linkRegExp.test(c.value);
      return valid ? null : {
        link: {
          valid: false
        }
      };
    }
    return null;
  }

  static integerPositive(c: AbstractControl) {
    if (c.value) {
      const numberRegExp = new RegExp('^[0-9]+$');
      const valid = numberRegExp.test(c.value);
      return valid ? null : {
        integerPositive: {
          valid: false
        }
      };
    }
    return null;
  }

  static decimalNumberPlusMinusPointOrComma(c: AbstractControl) {
    if (c.value) {
      const numberRegExp = new RegExp('^-?[0-9]+([\.,][0-9]{1,1000})?$');
      const valid = numberRegExp.test(c.value);
      return valid ? null : {
        decimalNumber: {
          valid: false
        }
      };
    }
    return null;
  }

  static decimalNumberPlusMinusPointOrCommaWithTwoDecimalPlaces(c: AbstractControl) {
    if (c.value) {
      const numberRegExp = new RegExp('^-?[0-9]+([\.,][0-9]{0,2})?$');
      const valid = numberRegExp.test(c.value);
      return valid ? null : {
        decimalNumberSpecificDecimalPlaces: {
          valid: false
        }
      };
    }
    return null;
  }
}
