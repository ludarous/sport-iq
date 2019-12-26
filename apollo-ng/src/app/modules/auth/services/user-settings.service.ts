import {Injectable} from '@angular/core';
import {Md5} from 'ts-md5';
import {ModuleConfig} from '../../config';
import {AuthService} from './auth.service';

export interface UserSettings {
    settings: any;
}

@Injectable()
export class UserSettingsService<T extends UserSettings> {

  constructor(private moduleConfig: ModuleConfig,
              private authenticationService: AuthService) {

  }

  get key(): string {
    const user = this.authenticationService.getCurrentUser();
    if (user) {
      const str: string = this.moduleConfig.environment.apiUrl + user.login;
      return Md5.hashStr(str) as string;
    }

    return null;
  }

  getSettings(): T {
    let settings = this.getObject(this.key);
    if (!settings) {
      settings = {} as T;
      if (this.key) {
        this.setObject(this.key, settings);
      }
    }
    return settings;
  }

  setSettings(userSettings: T) {
    if (this.key) {
      this.setObject(this.key, userSettings);
    }
  }


  setObject(key: string, obj: any) {
    localStorage.setItem(key, JSON.stringify(obj));
  }

  getObject(key: string): T {
    try {
      return JSON.parse(localStorage.getItem(key)) as T;
    } catch (error) {
      console.error('Local storage parsing error: key: ' + key);
      return null;
    }
  }

}
