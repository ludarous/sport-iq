import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JHipster6105TestModule } from '../../../test.module';
import { UserEventDetailComponent } from 'app/entities/user-event/user-event-detail.component';
import { UserEvent } from 'app/shared/model/user-event.model';

describe('Component Tests', () => {
  describe('UserEvent Management Detail Component', () => {
    let comp: UserEventDetailComponent;
    let fixture: ComponentFixture<UserEventDetailComponent>;
    const route = ({ data: of({ userEvent: new UserEvent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JHipster6105TestModule],
        declarations: [UserEventDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserEventDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserEventDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userEvent on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userEvent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
