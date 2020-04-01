import { TestBed, async } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { DashboardComponent } from './dashboard.component';
import { DashboardMenuComponent, AppSubMenuComponent } from './dashboard.menu.component';
import { DashboardTopbarComponent } from './dashboard.topbar.component';
import { DashboardFooterComponent } from './dashboard.footer.component';
import { DashboardBreadcrumbComponent } from './dashboard.breadcrumb.component';
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
        DashboardComponent,
        DashboardMenuComponent,
        AppSubMenuComponent,
        DashboardTopbarComponent,
        DashboardFooterComponent,
        DashboardBreadcrumbComponent
      ],
      providers: [BreadcrumbService]
    }).compileComponents();
  }));

  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(DashboardComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
