export class LogicUtils {
  public static nullOrUndefined(value: any) {
    return value === null || value === undefined;
  }

  public static nullOrUndefinedOrEmpty(value: any) {
    return value === null || value === undefined || value === '';
  }
}
