import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventLocationsListComponent } from './event-locations-list.component';

describe('ActivityCategoriesListComponent', () => {
  let component: EventLocationsListComponent;
  let fixture: ComponentFixture<EventLocationsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventLocationsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventLocationsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
