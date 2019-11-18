export class ArrayUtils {

  static getLastItem<T>(array: Array<T> ): T {
    if (array && array.length > 0) {
      return array[array.length - 1];
    } else {
      return null;
    }
  }

  static getFirstItem<T>(array: Array<T> ): T {
    if (array && array.length > 0) {
      return array[0];
    } else {
      return null;
    }
  }

  static addItem<T>(array: Array<T>, item: T) {
    if (array) {
      array.push(item);
    }
  }

  static addNewItem<T>(array: Array<T>, item: T) {
   if (array && !array.some(i => i === item)) {
     array.push(item);
   }
  }

  static insertItem<T>(array: Array<T>, item: T, index: number) {
    if (array) {
      array.splice(index, 0, item);
    }
  }

  static removeItem<T>(array: Array<T>, item: T) {
    if (array) {
      const itemIndex = array.indexOf(item);
      if (itemIndex >= 0) {
        array.splice(itemIndex, 1);
      }
    }
  }

  static moveItem<T>(array: Array<T>, item: T, index: number) {
    if (array) {
      if (index < 0  || index === array.length) {
        return;
      }
      const currentIndex = array.indexOf(item);
      const indexes = [currentIndex, index].sort();
      array.splice(indexes[0], 2, array[indexes[1]], array[indexes[0]]);
    }
  }

  static move<T>(array: Array<T>, item: T, new_index: number) {
    let old_index = array.indexOf(item);

    while (old_index < 0) {
      old_index += array.length;
    }
    while (new_index < 0) {
      new_index += array.length;
    }
    if (new_index >= array.length) {
      let k = new_index - array.length;
      while ((k--) + 1) {
        array.push(undefined);
      }
    }
    array.splice(new_index, 0, array.splice(old_index, 1)[0]);
    return array;
  }

  static clear<T>(array: Array<T>) {
    if (array) {
      array.splice(0, array.length);
    }
  }

  static flatten<T>(array: Array<T>, childrenPropName: string) {
    if (array) {
      const flattenedArray = new Array<T>();
      for (const i of array) {
        flattenedArray.push(i);
        flattenedArray.push(...ArrayUtils.flatten(<Array<T>>i[childrenPropName], childrenPropName));
      }
      return flattenedArray;
    }
    return array;
  }

  static setChildrenParent<T>(item: T, childrenPropName: string, parentPropName: string) {

    const children: Array<T> = <Array<T>>item[childrenPropName];

    if (children) {
      for (const i of children) {
        i[parentPropName] = item;
        ArrayUtils.setChildrenParent(i, childrenPropName, parentPropName);
      }
    }
  }

  static setChildrenParentForArray<T>(array: Array<T>, childrenPropName: string, parentPropName: string) {
    for (const i of array) {
      ArrayUtils.setChildrenParent(i, childrenPropName, parentPropName);
    }
  }

  static groupBy<K, T>(array: Array<T>, groupPropertyName: string): Map<K, Array<T>> {

    const groupByMap = new Map<K, Array<T>>();

    const reduced = array.reduce(function(rv, x) {
      (rv[x[groupPropertyName]] = rv[x[groupPropertyName]] || []).push(x);
      return rv;
    }, {});

    Object.keys(reduced).forEach(key => groupByMap.set(<K>(<any>key), reduced[key]));
    return groupByMap;
  }

  static sortByOtherArray<T>(array: Array<T>, patternArray: Array<T>) {
    if (array && patternArray) {

      const tmpArray = [...array];
      this.clear(array);

      for (const i of patternArray) {
        const existingItem = tmpArray.find(item => item === i);
        if (existingItem) {
          this.addNewItem(array, existingItem);
        }
      }
    }
    return array;
  }

  static copy2DArray(input: any[][]): any[][] {
    const copy: any[][] = JSON.parse(JSON.stringify(input));
    return copy;
  }

  static sortByNumber<T>(input: Array<T>, property: string, order = 1): Array<T> {
    if (input) {
      const comparer = function (o1: T, o2: T) {
        const result = o1[property] - o2[property];
        return result * order;
      };
      return input.sort(comparer);
    }
    return input;
  }

}
