import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EventsResultsComponent } from './events-results.component';

describe('ActivityCategoriesEditComponent', () => {
  let component: EventsResultsComponent;
  let fixture: ComponentFixture<EventsResultsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EventsResultsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EventsResultsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
