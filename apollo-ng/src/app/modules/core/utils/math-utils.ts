export class MathUtils {
  public static round(value, decimals) {
    const power = Math.pow(10, decimals);
    return Math.round(value * power) / power;
  }
}
