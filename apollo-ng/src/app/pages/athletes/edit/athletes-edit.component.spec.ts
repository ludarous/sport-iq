import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AthletesEditComponent } from './athletes-edit.component';

describe('ActivityCategoriesEditComponent', () => {
  let component: AthletesEditComponent;
  let fixture: ComponentFixture<AthletesEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AthletesEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AthletesEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
