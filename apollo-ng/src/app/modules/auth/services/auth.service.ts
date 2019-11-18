import {Observable} from 'rxjs';
import {IUser} from '../../entities/user';
import {AuthoritiesBase} from '../../entities/enums/authorities-base';
import {Injectable} from '@angular/core';

@Injectable()
export abstract class AuthService {

  abstract login(username?: string, password?: string): Observable<any>;
  abstract logout(): Observable<any>;
  abstract isLoggedIn(): boolean;
  abstract updateUser(): Observable<IUser>;
  abstract getCurrentUser(): IUser;
  abstract isCurrentUserInRole(roles: Array<AuthoritiesBase | string>, type?: string): boolean;
}
