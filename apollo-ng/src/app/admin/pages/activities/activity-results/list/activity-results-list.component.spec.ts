import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityResultsListComponent } from './activity-results-list.component';

describe('ListComponent', () => {
  let component: ActivityResultsListComponent;
  let fixture: ComponentFixture<ActivityResultsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActivityResultsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivityResultsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
