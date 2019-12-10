import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteEventsSummaryComponent } from './athlete-events-summary.component';

describe('AthleteEventsSummaryComponent', () => {
  let component: AthleteEventsSummaryComponent;
  let fixture: ComponentFixture<AthleteEventsSummaryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AthleteEventsSummaryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AthleteEventsSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
