import {Injectable, Optional} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from '@angular/common/http';
import {AuthUtils} from '../../core/utils/auth.utils';
import {IUser, User} from '../../entities/user';
import {ModuleConfig} from '../../config';
import {AuthoritiesBase} from '../../entities/enums/authorities-base';
import {environment} from '../../../../environments/environment';
import {catchError, map} from 'rxjs/operators';
import {Observable, throwError} from 'rxjs';
import {RxjsUtils} from '../../core/utils/rxjs.utils';
import {CsrfService} from '../services/csrf.service';
import { AuthService } from '../services/auth.service';

@Injectable()
export class OAuth2Service implements AuthService{

    constructor(@Optional() private config: ModuleConfig,
                private http: HttpClient,
                private csrfService: CsrfService) {
    }

    private currentUserId: number;
    private currentUser: IUser;


    login(): Observable<any> {
        location.href = environment.backendUrl + '/oauth2/authorization/oidc';
        return RxjsUtils.create(true);
    }

    isLoggedIn(): boolean {
        return !!this.getCurrentUser();
    }

    logout(): Observable<any> {

        // Add XSRF-TOKEN to request on logout
        const csrfToken = this.csrfService.getCsrfToken();

        let body = {};
        let headers = {};

        if (csrfToken) {
            body = {_csrf: csrfToken};
            headers = {'X-XSRF-TOKEN': csrfToken};
        }

        // logout from BE
        return this.http.post(this.config.environment.apiUrl + '/logout',
            body,
            {
                headers,
                observe: 'response'
            }).pipe(
            map((response: HttpResponse<any>) => {

                this.clearUser();
                // logout from keycloak
                location.href = environment.logoutUrl + '?redirect_uri=' + window.location.origin;
                return response;
            })
        );
    }

    public updateUser(): Observable<IUser> {
        const headers = new HttpHeaders({'Content-Type': 'application/json'});

        return this.http.get(this.config.environment.apiUrl + '/account', {headers, observe: 'response'})
            .pipe(map((currentUserResponse: HttpResponse<any>) => {

                    const csrfToken = currentUserResponse.headers.get(CsrfService.CSRF_TOKEN_HEADER);
                    this.csrfService.setCsrfToken(csrfToken);

                    this.currentUser = currentUserResponse.body;
                    this.currentUserId = this.currentUser.id;
                    this.saveUserToLocalStorage(this.currentUser);
                    if (this.config.appAuthoritiesEnum) {
                        this.currentUser = User.parseEnums(this.currentUser, this.config.appAuthoritiesEnum);
                    }
                    AuthUtils.userUpdatedSource.next(this.currentUser);
                    return this.currentUser;
                }), catchError((error: HttpErrorResponse, caught) => {
                    this.clearUser();
                    return throwError(error);
                })
            );
    }

    private saveUserToLocalStorage(user: IUser) {
        localStorage.setItem(this.config.userStorageKey, JSON.stringify(user));
    }

    private getLocalStorageUser(): IUser {
        let user = JSON.parse(localStorage.getItem(this.config.userStorageKey)) as IUser;
        if (this.config.appAuthoritiesEnum) {
            user = User.parseEnums(user, this.config.appAuthoritiesEnum);
        }
        return user;
    }

    clearUser() {
        this.currentUser = null;
        this.currentUserId = null;
        this.saveUserToLocalStorage(null);
        AuthUtils.userLoggedOutSource.next();
    }

    getCurrentUser(): IUser {
        let userChanged = false;
        if (!this.currentUser) {
            const user = this.getLocalStorageUser();
            if (user) {
                if (this.currentUserId !== user.id) {
                    userChanged = true;
                }
                this.currentUserId = user.id;
                this.currentUser = user;
            }
        }
        if (userChanged) {
            AuthUtils.userChangedSource.next(this.currentUser);
        }
        return this.currentUser;
    }

    isCurrentUserInRole(roles: Array<AuthoritiesBase | string>, type?: string): boolean {
        return AuthUtils.isUserInRole(this.getCurrentUser(), roles, type);
    }

}
