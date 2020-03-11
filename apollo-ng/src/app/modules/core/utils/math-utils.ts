export class MathUtils {
  public static round(value, decimals) {
    const power = Math.pow(10, decimals);
    return Math.round(value * power) / power;
  }

  public static sum(numbers: Array<number>): number {
      if (numbers && numbers.length > 0) {
          return numbers.reduce((a, b) => a + b, 0);
      }
      return 0;
 }
}
