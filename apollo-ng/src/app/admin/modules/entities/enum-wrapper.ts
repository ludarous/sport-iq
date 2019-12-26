/**
 * Created by RLU on 26.06.2017.
 */

export class EnumWrapper {

  private enumObject: any;
  ordinal: number;
  value: string;
  data: any;


  constructor(enumObject: any, value?: number | string, data?: any) {
    if (enumObject) {
      this.enumObject = enumObject;
      this.data = data;
      if ((typeof value) === 'string') {
        this.value = <string>value;
        this.ordinal = enumObject[value];
      } else {
        this.ordinal = <number>value;
        this.value = enumObject[value];
      }
    } else {
      throw new TypeError();
    }
  }

  getOrdinal(): number {
    return this.ordinal;
  }

  toString(): string {
    return this.value;
  }

  toJSON(): any {
    return this.value;
  }

  equals(obj: EnumWrapper): boolean {

    if (!obj) { return false; }

    if (!isNaN(obj.ordinal) && obj.value) {
      if (this.ordinal === obj.ordinal && this.value === obj.value) {
        return true;
      } else {
        return false;
      }
    } else  {
      return this.ordinal === <number><any>obj || this.value === <string><any>obj;
    }
    // Code for comparing with Enum objects, TODO: communicate with othersr
  }

  compareTo(obj: EnumWrapper): number {
    return this.ordinal > obj.ordinal ? 1 : this.ordinal < obj.ordinal ? -1 : 0;
  }

  greaterThan(obj: EnumWrapper): boolean {
    if (this.ordinal > obj.ordinal) return true;
    else return false;
  }

  greaterThanOrEqualTo(obj: EnumWrapper): boolean {
    if (this.ordinal >= obj.ordinal) return true;
    else return false;
  }

  lesserThan(obj: EnumWrapper): boolean {
    if (this.ordinal < obj.ordinal) return true;
    else return false;
  }

  lesserThanOrEqualTo(obj: EnumWrapper): boolean {
    if (this.ordinal <= obj.ordinal) return true;
    else return false;
  }

}
