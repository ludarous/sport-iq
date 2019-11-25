import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { AppRoutes } from './app.routes';

import { AppComponent } from './app.component';
import { AppMenuComponent, AppSubMenuComponent } from './app.menu.component';
import { AppTopBarComponent } from './app.topbar.component';
import { AppFooterComponent } from './app.footer.component';
import { AppBreadcrumbComponent } from './app.breadcrumb.component';
import { DashboardDemoComponent } from './demo/view/dashboarddemo.component';
import { SampleDemoComponent } from './demo/view/sampledemo.component';
import { FormsDemoComponent } from './demo/view/formsdemo.component';
import { DataDemoComponent } from './demo/view/datademo.component';
import { PanelsDemoComponent } from './demo/view/panelsdemo.component';
import { OverlaysDemoComponent } from './demo/view/overlaysdemo.component';
import { MenusDemoComponent } from './demo/view/menusdemo.component';
import { MessagesDemoComponent } from './demo/view/messagesdemo.component';
import { ChartsDemoComponent } from './demo/view/chartsdemo.component';
import { FileDemoComponent } from './demo/view/filedemo.component';
import { MiscDemoComponent } from './demo/view/miscdemo.component';
import { EmptyDemoComponent } from './demo/view/emptydemo.component';
import { DocumentationComponent } from './demo/view/documentation.component';

import { CarService } from './demo/service/carservice';
import { CountryService } from './demo/service/countryservice';
import { EventService } from './demo/service/eventservice';
import { NodeService } from './demo/service/nodeservice';
import { BreadcrumbService } from './breadcrumb.service';
import {PrimeNgComponentsModule} from './shared/prime-ng-components.module';
import {environment} from '../environments/environment';
import {AuthOAuth2Module} from './modules/auth/oauth2/auth.oauth2.module';
import {AuthoritiesEnum} from './entities/authorities';
import {EnumTranslationsModule} from './modules/shared-components/enum-translations.module';
import {EnumTranslatorServiceImpl} from './services/enum-translator.service';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {CoreModule} from './modules/core/core.module';

export function createTranslateLoader(http: HttpClient) {
    return new TranslateHttpLoader(http, 'assets/i18n/', '.json');
}

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        AppRoutes,
        HttpClientModule,
        BrowserAnimationsModule,

        PrimeNgComponentsModule,

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
    ],
    declarations: [
        AppComponent,
        AppMenuComponent,
        AppSubMenuComponent,
        AppTopBarComponent,
        AppFooterComponent,
        AppBreadcrumbComponent,
        DashboardDemoComponent,
        SampleDemoComponent,
        FormsDemoComponent,
        DataDemoComponent,
        PanelsDemoComponent,
        OverlaysDemoComponent,
        MenusDemoComponent,
        MessagesDemoComponent,
        ChartsDemoComponent,
        FileDemoComponent,
        MiscDemoComponent,
        EmptyDemoComponent,
        DocumentationComponent,
    ],
    providers: [
        { provide: LocationStrategy, useClass: HashLocationStrategy },
        CarService,
        CountryService,
        EventService,
        NodeService,
        BreadcrumbService,
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
