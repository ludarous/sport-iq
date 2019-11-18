import {Observable} from 'rxjs';

export class  RxjsUtils {

  static create(obj: any): Observable<any> {
    const result = Observable.create((observer) => {
      observer.next(obj);
    });
    return result;
  }

}
