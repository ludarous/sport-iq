import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SportIqTestModule } from '../../../test.module';
import { BodyCharacteristicsUpdateComponent } from 'app/entities/body-characteristics/body-characteristics-update.component';
import { BodyCharacteristicsService } from 'app/entities/body-characteristics/body-characteristics.service';
import { BodyCharacteristics } from 'app/shared/model/body-characteristics.model';

describe('Component Tests', () => {
  describe('BodyCharacteristics Management Update Component', () => {
    let comp: BodyCharacteristicsUpdateComponent;
    let fixture: ComponentFixture<BodyCharacteristicsUpdateComponent>;
    let service: BodyCharacteristicsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportIqTestModule],
        declarations: [BodyCharacteristicsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BodyCharacteristicsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BodyCharacteristicsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BodyCharacteristicsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BodyCharacteristics(123);
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
        const entity = new BodyCharacteristics();
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
