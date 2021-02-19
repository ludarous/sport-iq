import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JHipster6105TestModule } from '../../../test.module';
import { LegalRepresentativeUpdateComponent } from 'app/entities/legal-representative/legal-representative-update.component';
import { LegalRepresentativeService } from 'app/entities/legal-representative/legal-representative.service';
import { LegalRepresentative } from 'app/shared/model/legal-representative.model';

describe('Component Tests', () => {
  describe('LegalRepresentative Management Update Component', () => {
    let comp: LegalRepresentativeUpdateComponent;
    let fixture: ComponentFixture<LegalRepresentativeUpdateComponent>;
    let service: LegalRepresentativeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JHipster6105TestModule],
        declarations: [LegalRepresentativeUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LegalRepresentativeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LegalRepresentativeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LegalRepresentativeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LegalRepresentative(123);
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
        const entity = new LegalRepresentative();
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
