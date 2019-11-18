import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityCategoriesComponent } from './activity-categories.component';

describe('WorkoutCategoriesComponent', () => {
  let component: ActivityCategoriesComponent;
  let fixture: ComponentFixture<ActivityCategoriesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActivityCategoriesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivityCategoriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
