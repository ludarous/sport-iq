import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CountToTimeComponent } from './count-to-time.component';

describe('CountToTimeComponent', () => {
  let component: CountToTimeComponent;
  let fixture: ComponentFixture<CountToTimeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CountToTimeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CountToTimeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
