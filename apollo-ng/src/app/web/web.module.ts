import { NgModule } from '@angular/core';

import { WebRoutingModule } from './web-routing.module';
import { WebComponent } from './web.component';
import { LoaderComponent } from './loader/loader.component';
import { MenuComponent } from './menu/menu.component';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { TechnologiesComponent } from './technologies/technologies.component';
import { SkillsComponent } from './skills/skills.component';
import { SkillSlideComponent } from './skills/skill-slide/skill-slide.component';
import { CountToDirective } from './shared/directives/count-to.directive';
import { NgbCarouselModule, NgbTabsetModule } from '@ng-bootstrap/ng-bootstrap';
import { ContactsComponent } from './contacts/contacts.component';
import { FooterComponent } from './footer/footer.component';
import { TestsComponent } from './tests/tests.component';
import { NgwWowModule } from 'ngx-wow';
import { CountToTimeComponent } from './shared/count-to-time/count-to-time.component';
import { CountToTimeService } from './shared/count-to-time/count-to-time.service';
import { TranslateModule } from '@ngx-translate/core';
import { CommonModule } from '@angular/common';
import { TestDetailComponent } from './tests/test-detail/test-detail.component';


@NgModule({
    declarations: [
        WebComponent,
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
        CountToTimeComponent,
        TestDetailComponent
    ],
    imports: [
        CommonModule,
        WebRoutingModule,
        NgbCarouselModule,
        NgbTabsetModule,
        NgwWowModule,
        TranslateModule,
    ],
    providers: [
        CountToTimeService,
    ]
})
export class WebModule {
}
