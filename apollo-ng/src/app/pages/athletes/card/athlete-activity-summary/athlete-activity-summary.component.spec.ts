import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteActivitySummaryComponent } from './athlete-activity-summary.component';

describe('AthleteActivitySummaryComponent', () => {
  let component: AthleteActivitySummaryComponent;
  let fixture: ComponentFixture<AthleteActivitySummaryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AthleteActivitySummaryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AthleteActivitySummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
