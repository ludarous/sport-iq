import {IUser} from '../../entities/user';
import {Subject} from 'rxjs';
import {AuthoritiesBase} from '../../entities/enums/authorities-base';
import {IToken} from '../../entities/token';
import { environment } from '../../../../environments/environment';

export class AuthUtils {

  public static termsNotApprovedSource = new Subject();
  public static termsNotApproved$ = AuthUtils.termsNotApprovedSource.asObservable();

  public static userUnauthorizedSource = new Subject();
  public static userUnauthorized$ = AuthUtils.userUnauthorizedSource.asObservable();

  public static userLoggedOutSource = new Subject();
  public static userLoggedOut$ = AuthUtils.userLoggedOutSource.asObservable();

  public static userLoggedInSource = new Subject<IUser>();
  public static userLoggedIn$ = AuthUtils.userLoggedInSource.asObservable();

  public static userChangedSource = new Subject<IUser>();
  public static userChanged$ = AuthUtils.userChangedSource.asObservable();

  public static userUpdatedSource = new Subject<IUser>();
  public static userUpdated$ = AuthUtils.userUpdatedSource.asObservable();

  static isUserInRole(user: IUser, roles: Array<AuthoritiesBase | string>, type?: string): boolean {
    type = type ? type : 'or';

    if (!roles || roles.length <= 0) {
      return true;
    }

    if (user) {
      let isInRole;

      if (type === 'and') {
        for (const role of roles) {
          const roleToEqual = role instanceof AuthoritiesBase  ? role : new AuthoritiesBase(environment.authoritiesEnum, role);
          isInRole = user.authorities.some((a) => a.equals(roleToEqual)) && isInRole;
        }
        return isInRole;
      } else if (type === 'or') {
        for (const role of roles) {
          // isInRole = user.authorities.indexOf(role) >= 0
          const roleToEqual = role instanceof AuthoritiesBase  ? role : new AuthoritiesBase(environment.authoritiesEnum, role);
          isInRole = user.authorities.some((a) => a.equals(roleToEqual));
          if (isInRole) {
            return isInRole;
          }
        }
      }
    }
    return false;

  }

  static getTokenFromResponse(response: Response): any {
    try {
      const token = response && (<any>response).id_token;
      if (token) {
        return token;
      }
    } catch (err) {
      return null;
    }
  }

  static userRolesSameAsTokenRoles(user: IUser, token: IToken): boolean {

    const userRoles = user.authorities;
    if (userRoles.length !== token.authorities.length) {
      return false;
    }

    const tmp1 = userRoles.filter((ur) => !token.authorities.some((tk) => tk.equals(ur)));
    const tmp2 = token.authorities.filter((tr) => !userRoles.some((ur) => ur.equals(tr)));

    if (tmp1.length > 0 || tmp2.length > 0) {
      return false;
    }

    return true;

  }
}

