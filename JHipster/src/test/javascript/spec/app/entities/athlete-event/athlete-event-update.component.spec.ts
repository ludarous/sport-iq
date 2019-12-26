import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SportiqTestModule } from '../../../test.module';
import { AthleteEventUpdateComponent } from 'app/entities/athlete-event/athlete-event-update.component';
import { AthleteEventService } from 'app/entities/athlete-event/athlete-event.service';
import { AthleteEvent } from 'app/shared/model/athlete-event.model';

describe('Component Tests', () => {
  describe('AthleteEvent Management Update Component', () => {
    let comp: AthleteEventUpdateComponent;
    let fixture: ComponentFixture<AthleteEventUpdateComponent>;
    let service: AthleteEventService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportiqTestModule],
        declarations: [AthleteEventUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AthleteEventUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AthleteEventUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AthleteEventService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AthleteEvent(123);
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
        const entity = new AthleteEvent();
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
