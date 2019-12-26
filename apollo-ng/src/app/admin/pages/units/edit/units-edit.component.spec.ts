import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UnitsEditComponent } from './units-edit.component';

describe('ActivityCategoriesEditComponent', () => {
  let component: UnitsEditComponent;
  let fixture: ComponentFixture<UnitsEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UnitsEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UnitsEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
