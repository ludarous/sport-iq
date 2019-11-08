import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoaderComponent } from './loader/loader.component';
import { MenuComponent } from './menu/menu.component';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { TechnologiesComponent } from './technologies/technologies.component';
import { SkillsComponent } from './skills/skills.component';
import { SkillSlideComponent } from './skills/skill-slide/skill-slide.component';
import {CountToDirective} from './shared/directives/count-to.directive';
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';
import { ContactsComponent } from './contacts/contacts.component';
import { FooterComponent } from './footer/footer.component';

@NgModule({
  declarations: [
    AppComponent,
    LoaderComponent,
    MenuComponent,
    HomeComponent,
    AboutComponent,
    TechnologiesComponent,
    SkillsComponent,
    SkillSlideComponent,
    CountToDirective,
    ContactsComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbCarouselModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
