import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AthletesListComponent } from './athletes-list.component';

describe('ActivityCategoriesListComponent', () => {
  let component: AthletesListComponent;
  let fixture: ComponentFixture<AthletesListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AthletesListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AthletesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
