import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteActivityResultSplitComponent } from './athlete-activity-result-split.component';

describe('ActivityCategoriesEditComponent', () => {
  let component: AthleteActivityResultSplitComponent;
  let fixture: ComponentFixture<AthleteActivityResultSplitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AthleteActivityResultSplitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AthleteActivityResultSplitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
