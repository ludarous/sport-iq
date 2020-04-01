import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthOAuth2Module } from './modules/auth/oauth2/auth.oauth2.module';
import { environment } from '../environments/environment';
import { AuthoritiesEnum } from './dashboard/entities/authorities';
import { EnumTranslationsModule } from './modules/shared-components/enum-translations.module';
import { EnumTranslatorServiceImpl } from './dashboard/services/enum-translator.service';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { CoreModule } from './modules/core/core.module';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { BrowserModule } from '@angular/platform-browser';
import { ProtractorBrowser } from 'protractor';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { WebModule } from './web/web.module';

export function createTranslateLoader(http: HttpClient) {
    return new TranslateHttpLoader(http, 'assets/i18n/', '.json');
}

@NgModule({
    declarations: [AppComponent],
    imports: [
        CommonModule,
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,

        AuthOAuth2Module.forRoot({environment, appAuthoritiesEnum: AuthoritiesEnum}),
        EnumTranslationsModule.forRoot(EnumTranslatorServiceImpl),
        TranslateModule.forRoot({
            useDefaultLang: false,
            loader: {
                provide: TranslateLoader,
                useFactory: createTranslateLoader,
                deps: [HttpClient]
            }
        }),

        CoreModule,
        WebModule
    ],
    bootstrap: [
        AppComponent
    ]
})
export class AppModule {
}
