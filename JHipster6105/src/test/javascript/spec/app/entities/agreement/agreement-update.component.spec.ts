import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JHipster6105TestModule } from '../../../test.module';
import { AgreementUpdateComponent } from 'app/entities/agreement/agreement-update.component';
import { AgreementService } from 'app/entities/agreement/agreement.service';
import { Agreement } from 'app/shared/model/agreement.model';

describe('Component Tests', () => {
  describe('Agreement Management Update Component', () => {
    let comp: AgreementUpdateComponent;
    let fixture: ComponentFixture<AgreementUpdateComponent>;
    let service: AgreementService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JHipster6105TestModule],
        declarations: [AgreementUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AgreementUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AgreementUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AgreementService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Agreement(123);
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
        const entity = new Agreement();
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
