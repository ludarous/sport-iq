import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JHipster6105TestModule } from '../../../test.module';
import { UserEventUpdateComponent } from 'app/entities/user-event/user-event-update.component';
import { UserEventService } from 'app/entities/user-event/user-event.service';
import { UserEvent } from 'app/shared/model/user-event.model';

describe('Component Tests', () => {
  describe('UserEvent Management Update Component', () => {
    let comp: UserEventUpdateComponent;
    let fixture: ComponentFixture<UserEventUpdateComponent>;
    let service: UserEventService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JHipster6105TestModule],
        declarations: [UserEventUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserEventUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserEventUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserEventService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserEvent(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserEvent();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
