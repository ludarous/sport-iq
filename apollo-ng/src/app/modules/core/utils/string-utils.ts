import {EnumTranslatorService} from '../../shared-components/services/enum-translator.service';
import {EnumWrapper} from '../../entities/enum-wrapper';

export class StringUtils {

  static sortByStrings(s1: string, s2: string, order: number = 1): number {
    let val = 0;
    if (s1 < s2) val = -1;
    else if (s1 > s2) val = 1;
    return val * order;
  }

  static sortBy2Strings(object1string1: string, object1string2: string, object2string1: string, object2string2: string,
                          order: number = 1): number {
    let val = 0;
    // compare by first string of each objects
    if (object1string1 < object2string1) val = -1;
    else if (object1string1 > object2string1) val = 1;
    else {
      // compare by second string of each objects
      if (object1string2 < object2string2) val = -1;
      else if (object1string2 > object2string2) val = 1;
    }
    return val * order;
  }

  static sortBy2StringsAndOneNumber(object1string1: string, object1string2: string, object1number1: number,
                                    object2string1: string, object2string2: string, object2number1: number,
                                    order: number = 1): number {
    let val = 0;
    // compare by first string of each objects
    if (object1string1 < object2string1) val = -1;
    else if (object1string1 > object2string1) val = 1;
    else {
      // compare by second string of each objects
      if (object1string2 < object2string2) val = -1;
      else if (object1string2 > object2string2) val = 1;
      else {
        // compare by number
        const object1number1NotNull = object1number1 === null ? 0 : object1number1;
        const object2number1NotNull = object2number1 === null ? 0 : object2number1;
        if (object1number1NotNull < object2number1NotNull) val = -1;
        else if (object1number1NotNull > object2number1NotNull) val = 1;
      }
    }
    return val * order;
  }

  static sortBy1StringAndOneNumber(object1string1: string, object1number1: number,
                                   object2string1: string, object2number1: number,
                                   order: number = 1): number {
    let val = 0;
    // compare by string of each objects
    if (object1string1 < object2string1) val = -1;
    else if (object1string1 > object2string1) val = 1;
    else {
      // compare by number
      const object1number1NotNull = object1number1 === null ? 0 : object1number1;
      const object2number1NotNull = object2number1 === null ? 0 : object2number1;
      if (object1number1NotNull < object2number1NotNull) val = -1;
      else if (object1number1NotNull > object2number1NotNull) val = 1;
    }
    return val * order;
  }

  static sortByLocalizedEnumsAlphabetically(
    enumTranslatorService: EnumTranslatorService,
    e1: EnumWrapper,
    e2: EnumWrapper,
    order: number = 1
  ): number {
    const o1LocalName = enumTranslatorService.translate(e1);
    const o2LocalName = enumTranslatorService.translate(e2);
    return StringUtils.sortByStrings(o1LocalName, o2LocalName, order);
  }

}
