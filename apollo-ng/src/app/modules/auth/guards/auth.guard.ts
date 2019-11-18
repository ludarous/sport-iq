import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, CanLoad, Route, Router, RouterStateSnapshot} from '@angular/router';
import {AuthoritiesBase} from '../../entities/enums/authorities-base';
import {AuthService} from '../services/auth.service';

@Injectable()
export class AuthGuard implements CanActivate, CanLoad {

  constructor(private router: Router,
              private authenticationService: AuthService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
   return this.checkAccess(route.data['authorities'], state.url);
  }

  canLoad(route: Route): boolean {
    return this.checkAccess(route.data['authorities'], route.path);
  }

  checkAccess(authorities: Array<string>, path: string): boolean {
    if (this.authenticationService.isLoggedIn()) {
      if (this.authenticationService.isCurrentUserInRole(authorities)) {
        return true;
      } else {
        console.log('User ' + this.authenticationService.getCurrentUser().login + ' does not have roles ' + authorities.map(a => a.toString() + ', '));
        return false;
      }
    } else {
      return false;
    }
  }
}
