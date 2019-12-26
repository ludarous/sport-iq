import { TestBed, async } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AdminComponent } from './admin.component';
import { AdminMenuComponent, AppSubMenuComponent } from './admin.menu.component';
import { AdminTopbarComponent } from './admin.topbar.component';
import { AdminFooterComponent } from './admin.footer.component';
import { AdminBreadcrumbComponent } from './admin.breadcrumb.component';
import { BreadcrumbService } from './breadcrumb.service';
import {InputSwitchModule, ProgressBarModule, ScrollPanelModule} from 'primeng/primeng';
import {FormsModule} from '@angular/forms';

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        ProgressBarModule,
        InputSwitchModule,
        FormsModule,
        ScrollPanelModule
      ],
      declarations: [
        AdminComponent,
        AdminMenuComponent,
        AppSubMenuComponent,
        AdminTopbarComponent,
        AdminFooterComponent,
        AdminBreadcrumbComponent
      ],
      providers: [BreadcrumbService]
    }).compileComponents();
  }));

  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AdminComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
