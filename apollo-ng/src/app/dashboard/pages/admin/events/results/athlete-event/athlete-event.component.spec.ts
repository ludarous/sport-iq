import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteEventComponent } from './athlete-event.component';

describe('ActivityCategoriesEditComponent', () => {
  let component: AthleteEventComponent;
  let fixture: ComponentFixture<AthleteEventComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AthleteEventComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AthleteEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
