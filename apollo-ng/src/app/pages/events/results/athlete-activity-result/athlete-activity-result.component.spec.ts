import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteActivityResultComponent } from './athlete-activity-result.component';

describe('ActivityCategoriesEditComponent', () => {
  let component: AthleteActivityResultComponent;
  let fixture: ComponentFixture<AthleteActivityResultComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AthleteActivityResultComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AthleteActivityResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
