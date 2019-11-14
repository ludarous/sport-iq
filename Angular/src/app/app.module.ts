import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoaderComponent} from './loader/loader.component';
import {MenuComponent} from './menu/menu.component';
import {HomeComponent} from './home/home.component';
import {AboutComponent} from './about/about.component';
import {TechnologiesComponent} from './technologies/technologies.component';
import {SkillsComponent} from './skills/skills.component';
import {SkillSlideComponent} from './skills/skill-slide/skill-slide.component';
import {CountToDirective} from './shared/directives/count-to.directive';
import {NgbCarouselModule} from '@ng-bootstrap/ng-bootstrap';
import {ContactsComponent} from './contacts/contacts.component';
import {FooterComponent} from './footer/footer.component';
import {TestsComponent} from './tests/tests.component';
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {NgwWowModule} from 'ngx-wow';
import {CountToTimeComponent} from './shared/count-to-time/count-to-time.component';
import {CountToTimeService} from './shared/count-to-time/count-to-time.service';

export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, 'assets/i18n/', '.json');
}

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
    FooterComponent,
    TestsComponent,
    CountToTimeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbCarouselModule,
    HttpClientModule,
    NgwWowModule,
    TranslateModule.forRoot({
      useDefaultLang: false,
      loader: {
        provide: TranslateLoader,
        useFactory: createTranslateLoader,
        deps: [HttpClient]
      }
    }),
  ],
  providers: [
    CountToTimeService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
