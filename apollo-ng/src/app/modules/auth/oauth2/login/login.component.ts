import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from '../../../../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Component({
    template: ''
})
export class LoginComponent {

    environment = environment;

    constructor(private authService: AuthService,
                private router: Router,
                private activatedRoute: ActivatedRoute,
                private httpClient: HttpClient) {
        if (this.allowedReferrer(document.referrer)) {
            this.authService.login();
        } else {
            this.router.navigate(['']);
        }

        this.activatedRoute.params.subscribe((params) => {
            const state = params.state;
            const sessionState = params.session_state;
            const code = params.code;

            httpClient.get(environment.backendUrl + '/login/oauth2/code/oidc', { params: {
                    code: code,
                    session_state: sessionState,
                    state: state
                }
            });
        });
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
