import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SportiqTestModule } from '../../../test.module';
import { AthleteActivityResultUpdateComponent } from 'app/entities/athlete-activity-result/athlete-activity-result-update.component';
import { AthleteActivityResultService } from 'app/entities/athlete-activity-result/athlete-activity-result.service';
import { AthleteActivityResult } from 'app/shared/model/athlete-activity-result.model';

describe('Component Tests', () => {
  describe('AthleteActivityResult Management Update Component', () => {
    let comp: AthleteActivityResultUpdateComponent;
    let fixture: ComponentFixture<AthleteActivityResultUpdateComponent>;
    let service: AthleteActivityResultService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportiqTestModule],
        declarations: [AthleteActivityResultUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AthleteActivityResultUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AthleteActivityResultUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AthleteActivityResultService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AthleteActivityResult(123);
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
        const entity = new AthleteActivityResult();
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
