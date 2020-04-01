import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityResultsEditComponent } from './activity-results-edit.component';

describe('ActivityResultsEditComponent', () => {
  let component: ActivityResultsEditComponent;
  let fixture: ComponentFixture<ActivityResultsEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActivityResultsEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivityResultsEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
