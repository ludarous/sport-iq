import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteActivityComponent } from './athlete-activity.component';

describe('ActivityCategoriesEditComponent', () => {
  let component: AthleteActivityComponent;
  let fixture: ComponentFixture<AthleteActivityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AthleteActivityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AthleteActivityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
