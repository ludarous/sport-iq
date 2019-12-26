import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityCategoriesListComponent } from './activity-categories-list.component';

describe('ActivityCategoriesListComponent', () => {
  let component: ActivityCategoriesListComponent;
  let fixture: ComponentFixture<ActivityCategoriesListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActivityCategoriesListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivityCategoriesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
