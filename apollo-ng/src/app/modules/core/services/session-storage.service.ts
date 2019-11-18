import {Injectable} from '@angular/core';
import {isNull} from 'util';

@Injectable()
export class SessionStorageService {

  private SHOW_BANNER_KEY = 'SHOW_BANNER';

  getShowBanner(defaultValue?: boolean): boolean {
    const showBanner = this.getObject(this.SHOW_BANNER_KEY);
    if (isNull(showBanner) && defaultValue) {
      this.setShowBanner(defaultValue);
      return defaultValue;
    } else {
      return !!showBanner;
    }
  }

  setShowBanner(value: boolean) {
    this.setObject(this.SHOW_BANNER_KEY, value);
  }

  setObject(key: string, obj: any) {
    sessionStorage.setItem(key, JSON.stringify(obj));
  }

  getObject<T>(key: string): T {
    try {
      return <T>JSON.parse(sessionStorage.getItem(key));
    } catch (error) {
      console.error('Session storage parsing error: key: ' + key);
      return null;
    }
  }
}
