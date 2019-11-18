import {DatePipe} from '@angular/common';

/**
 * Created by pivik on 2017-06-07.
 */

export class CalendarUtils {

  // private static dateFormatForPipe: string = 'ddMM.yyyy';
  private static dateFormatForPipe = 'dd.MM.yyyy';
  private static dateTimeFormatForPipe = 'dd.MM.yyyy HH:mm:ss';
  private static timeFormatForPipe = 'HH:mm:ss';
  private static isoDateFormatForPipe = 'yyyy-MM-dd';
  public static dateFormat = 'dd.mm.yy';

  public static calendarLocale = {
    cs: {
      firstDayOfWeek: 1,
      dayNames: ['neděle', 'pondělí', 'úterý', 'středa', 'čtvrtek', 'pátek', 'sobota'],
      dayNamesShort: ['ne', 'po', 'út', 'st', 'čt', 'pá', 'so'],
      dayNamesMin: ['N', 'Po', 'Ú', 'St', 'Č', 'Pá', 'So'],
      monthNames: ['leden', 'únor', 'březen', 'duben', 'květen', 'červen', 'červenec', 'srpen', 'září', 'říjen', 'listopad', 'prosinec'],
      monthNamesShort: ['led', 'úno', 'bře', 'dub', 'kvě', 'čer', 'čec', 'srp', 'zář', 'říj', 'lis', 'pro']
    },
    sk: {
      firstDayOfWeek: 1,
      dayNames: ['nedeľa', 'pondelok', 'utorok', 'streda', 'štvrtok', 'piatok', 'sobota'],
      dayNamesShort: ['ne', 'po', 'ut', 'st', 'št', 'pi', 'so'],
      dayNamesMin: ['N', 'Po', 'U', 'St', 'Š', 'Pi', 'So'],
      monthNames: ['január', 'február', 'marec', 'apríl', 'máj', 'jún', 'júl', 'august', 'september', 'október', 'november', 'december'],
      monthNamesShort: ['jan', 'feb', 'mar', 'apr', 'máj', 'jún', 'júl', 'aug', 'sep', 'okt', 'nov', 'dec']
    }
  };

  static dateStringToLocalizedDateString(inputDate: string, lang: string = 'cs'): string {
    if (inputDate == null || inputDate.length <= 0) {
      return null;
    }
    const dateVal = new Date(inputDate);
    const datePipe = new DatePipe(lang);
    const result = datePipe.transform(dateVal, CalendarUtils.dateFormatForPipe);
    return result;
  }

  static currentLocalizedDateTimeString(lang: string = 'cs'): string {

    const inputDate = new Date();
    const datePipe = new DatePipe(lang);
    const result = datePipe.transform(inputDate, CalendarUtils.dateTimeFormatForPipe);

    return result;

  }

  static currentLocalizedTimeString(lang: string = 'cs'): string {

    const inputDate = new Date();
    const datePipe = new DatePipe(lang);
    const result = datePipe.transform(inputDate, CalendarUtils.timeFormatForPipe);

    return result;

  }

  static dateStringToLocalizedDateTimeString(inputDate: string, lang: string = 'cs'): string {
    if (inputDate == null || inputDate.length <= 0) {
      return null;
    }
    const dateVal = new Date(inputDate);
    const datePipe = new DatePipe(lang);
    const result = datePipe.transform(dateVal, CalendarUtils.dateTimeFormatForPipe);

    return result;
  }

  static dateStringToDate(inputDate: string): Date {
    if (inputDate == null || inputDate.length <= 0) {
      return null;
    }
    const dateVal = new Date(inputDate);
    return dateVal;
  }

  static localizedDateStringToDateString(inputDate: string): string {
    if (inputDate == null || inputDate.length <= 0) {
      return null;
    }
    const dateVal = new Date(inputDate);
    return dateVal.toISOString();
  }

  static dateToISOString(inputDate: Date): string {
    if (inputDate == null) {
      return null;
    }
    return inputDate.toISOString();
  }

  static dateToISODateString(inputDate: Date): string {
    if (inputDate == null) {
      return null;
    }
    const datePipe = new DatePipe('en');
    const result = datePipe.transform(inputDate, CalendarUtils.isoDateFormatForPipe);

    return result;
  }

  static sortByDateStrings(ds1: string, ds2: string, order: number = 1): number {

    let d1 = Date.parse(ds1);
    let d2 = Date.parse(ds2);

    d1 = d1 ? d1 : 0;
    d2 = d2 ? d2 : 0;

    return (d1 - d2) * order;
  }

  static sortBy2DateStrings(ds11: string, ds21: string, ds12: string, ds22: string, order: number = 1): number {
    const result = this.sortByDateStrings(ds11, ds21, order);
    if (result === 0) {
      return this.sortByDateStrings(ds12, ds22, order);
    }
    return result;
  }

  static isCurrentTimeBetweenDates(d1: Date, d2: Date): boolean {
    const now: Date = new Date();
    return (d1 <= now && now <= d2);
  }

  static isCurrentTimeBetweenDateStrings(d1: string, d2: string): boolean {
    const date1: Date = this.dateStringToDate(d1);
    const date2: Date = this.dateStringToDate(d2);
    return this.isCurrentTimeBetweenDates(date1, date2);
  }

  static getMonthName(monthNumber: number, language: string = 'cs'): string {
    return CalendarUtils.calendarLocale[language].monthNames[monthNumber - 1];
  }
}
