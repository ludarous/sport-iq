import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteActivityResultSummaryComponent } from './athlete-activity-result-summary.component';

describe('AthleteActivityResultSummaryComponent', () => {
  let component: AthleteActivityResultSummaryComponent;
  let fixture: ComponentFixture<AthleteActivityResultSummaryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AthleteActivityResultSummaryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AthleteActivityResultSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
