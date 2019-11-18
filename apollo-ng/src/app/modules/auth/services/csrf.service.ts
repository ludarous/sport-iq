import {Injectable} from '@angular/core';

@Injectable()
export class CsrfService {

  public static CSRF_TOKEN_HEADER = 'X-CSRF-TOKEN';

  private _csrfToken: string;

  getCsrfToken(): string {
    return this._csrfToken;
  }

  setCsrfToken(value: string) {
    this._csrfToken = value;
  }


}
