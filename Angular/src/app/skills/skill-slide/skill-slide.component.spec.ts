import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SkillSlideComponent } from './skill-slide.component';

describe('SkillSlideComponent', () => {
  let component: SkillSlideComponent;
  let fixture: ComponentFixture<SkillSlideComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkillSlideComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SkillSlideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
