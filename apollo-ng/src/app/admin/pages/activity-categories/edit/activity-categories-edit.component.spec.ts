import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityCategoriesEditComponent } from './activity-categories-edit.component';

describe('ActivityCategoriesEditComponent', () => {
  let component: ActivityCategoriesEditComponent;
  let fixture: ComponentFixture<ActivityCategoriesEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActivityCategoriesEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivityCategoriesEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
