import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventLocationsEditComponent } from './event-locations-edit.component';

describe('ActivityCategoriesEditComponent', () => {
  let component: EventLocationsEditComponent;
  let fixture: ComponentFixture<EventLocationsEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventLocationsEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventLocationsEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
