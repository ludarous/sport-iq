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
import { EventService } from '../admin/services/rest/event.service';
import { EventsComponent } from './events/events.component';
import { VcSharedComponentsModule } from '../modules/shared-components/vc-shared-components.module';
import { SingInModalComponent } from './shared/modals/sing-in-modal/sing-in-modal.component';
import { PrimeNgComponentsModule } from '../admin/shared/prime-ng-components.module';
import { DialogModule } from 'primeng/dialog';


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
        TestDetailComponent,
        EventsComponent,
        SingInModalComponent,
    ],
    imports: [
        CommonModule,
        WebRoutingModule,
        NgbCarouselModule,
        NgbTabsetModule,
        NgwWowModule,
        TranslateModule,
        VcSharedComponentsModule,
        DialogModule
    ],
    providers: [
        CountToTimeService,
        EventService
    ]
})
export class WebModule {
}
