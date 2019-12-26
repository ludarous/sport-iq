import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {ModuleWithProviders, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {UserService} from '../services/rest/user.service';
import {ModuleConfig} from '../../config';
import {AuthService} from '../services/auth.service';
import {OAuth2Service} from './auth.oauth2.service';
import {UserSettingsService} from '../services/user-settings.service';
import {AuthInterceptor} from './interceptors/auth.interceptor';
import {CsrfService} from '../services/csrf.service';
import {LoginComponent} from './login/login.component';
import {AuthOauth2RoutingModule} from './auth.oauth2-routing.module';

@NgModule({
  declarations: [
    LoginComponent,
  ],
  imports: [
    HttpClientModule,
    AuthOauth2RoutingModule,
    RouterModule,
  ],
  providers: [
    UserService,
    CsrfService
  ],
  exports: []
})
export class AuthOAuth2Module {
  /**
   * Use this method in your root module to provide the TranslateService
   */
  static forRoot(config?: ModuleConfig): ModuleWithProviders {
    return {
      ngModule: AuthOAuth2Module,
      providers: [
        {provide: ModuleConfig, useValue: config},
        {provide: AuthService, useClass: OAuth2Service},
        {provide: UserSettingsService, useClass: UserSettingsService},
        {
          provide: HTTP_INTERCEPTORS,
          useClass: AuthInterceptor,
          multi: true,
        },
      ]};
  }

}
