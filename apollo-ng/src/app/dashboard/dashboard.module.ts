import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { DashboardRoutes } from './dashboard.routes';

import { DashboardComponent } from './dashboard.component';
import { DashboardMenuComponent, AppSubMenuComponent } from './dashboard.menu.component';
import { DashboardTopbarComponent } from './dashboard.topbar.component';
import { DashboardFooterComponent } from './dashboard.footer.component';
import { DashboardBreadcrumbComponent } from './dashboard.breadcrumb.component';
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
import { PrimeNgComponentsModule } from './shared/prime-ng-components.module';
import { TranslateModule } from '@ngx-translate/core';
import { AvatarModule } from 'ngx-avatar';


@NgModule({
    imports: [
        DashboardRoutes,
        TranslateModule,
        PrimeNgComponentsModule,
        AvatarModule
    ],
    declarations: [
        DashboardComponent,
        DashboardMenuComponent,
        AppSubMenuComponent,
        DashboardTopbarComponent,
        DashboardFooterComponent,
        DashboardBreadcrumbComponent,
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
    ]
})
export class DashboardModule { }
