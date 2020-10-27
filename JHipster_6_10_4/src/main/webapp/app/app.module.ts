import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { SportIqSharedModule } from 'app/shared/shared.module';
import { SportIqCoreModule } from 'app/core/core.module';
import { SportIqAppRoutingModule } from './app-routing.module';
import { SportIqHomeModule } from './home/home.module';
import { SportIqEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    SportIqSharedModule,
    SportIqCoreModule,
    SportIqHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    SportIqEntityModule,
    SportIqAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class SportIqAppModule {}
