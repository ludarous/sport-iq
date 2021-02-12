import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { JHipster6105SharedModule } from 'app/shared/shared.module';
import { JHipster6105CoreModule } from 'app/core/core.module';
import { JHipster6105AppRoutingModule } from './app-routing.module';
import { JHipster6105HomeModule } from './home/home.module';
import { JHipster6105EntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    JHipster6105SharedModule,
    JHipster6105CoreModule,
    JHipster6105HomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    JHipster6105EntityModule,
    JHipster6105AppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class JHipster6105AppModule {}
