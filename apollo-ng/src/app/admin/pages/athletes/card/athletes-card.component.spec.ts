import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AthletesCardComponent } from './athletes-card.component';

describe('ActivityCategoriesEditComponent', () => {
  let component: AthletesCardComponent;
  let fixture: ComponentFixture<AthletesCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AthletesCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AthletesCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
