import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteWorkoutSummaryComponent } from './athlete-workout-summary.component';

describe('AthleteWorkoutSummaryComponent', () => {
  let component: AthleteWorkoutSummaryComponent;
  let fixture: ComponentFixture<AthleteWorkoutSummaryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AthleteWorkoutSummaryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AthleteWorkoutSummaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
