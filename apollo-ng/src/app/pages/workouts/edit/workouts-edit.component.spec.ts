import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { WorkoutsEditComponent } from './workouts-edit.component';

describe('ActivityCategoriesEditComponent', () => {
  let component: WorkoutsEditComponent;
  let fixture: ComponentFixture<WorkoutsEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ WorkoutsEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkoutsEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
