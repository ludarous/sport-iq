import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteInfoComponent } from './athlete-info.component';

describe('AthleteInfoComponent', () => {
  let component: AthleteInfoComponent;
  let fixture: ComponentFixture<AthleteInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AthleteInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AthleteInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
