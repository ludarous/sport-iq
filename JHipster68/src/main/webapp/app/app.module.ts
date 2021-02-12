import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { JHipster68SharedModule } from 'app/shared/shared.module';
import { JHipster68CoreModule } from 'app/core/core.module';
import { JHipster68AppRoutingModule } from './app-routing.module';
import { JHipster68HomeModule } from './home/home.module';
import { JHipster68EntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    JHipster68SharedModule,
    JHipster68CoreModule,
    JHipster68HomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    JHipster68EntityModule,
    JHipster68AppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class JHipster68AppModule {}
