import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivitesListComponent } from './activities-list.component';

describe('ActivityCategoriesListComponent', () => {
  let component: ActivitesListComponent;
  let fixture: ComponentFixture<ActivitesListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActivitesListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivitesListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
