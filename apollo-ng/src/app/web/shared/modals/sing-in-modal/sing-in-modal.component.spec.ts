import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SingInModalComponent } from './sing-in-modal.component';

describe('SingInModalComponent', () => {
  let component: SingInModalComponent;
  let fixture: ComponentFixture<SingInModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SingInModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SingInModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
