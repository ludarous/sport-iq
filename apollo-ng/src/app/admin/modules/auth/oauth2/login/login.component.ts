import {Component, OnInit} from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import {environment} from '../../../../../../environments/environment';

@Component({
  template: ''
})
export class LoginComponent {

  environment = environment;

  constructor(private authService: AuthService,
              private router: Router) {
    if (this.allowedReferrer(document.referrer)) {
      this.authService.login();
    } else {
      this.router.navigate(['']);
    }
  }


  allowedReferrer(referrer: string): boolean {
    if (referrer) {
      try {
        const referrerUrl = new URL(referrer);
        const currentUrl = window.location;
        if (currentUrl.hostname === referrerUrl.hostname && referrerUrl.pathname === '/login') {
          // Prevent infinite redirecting.
          return false;
        }
        return this.environment.allowedLoginReferrers.some(alr => alr === referrerUrl.hostname) || this.environment.allowedLoginReferrers.some(alr => alr === '*');
      } catch {
        console.warn('Cannot parse referrer. Login redirect not allowed');
        return false;
      }
    }

    // No referrer => allow login
    return true;
  }
}
