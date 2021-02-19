import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JHipster6105TestModule } from '../../../test.module';
import { ActivityResultSplitUpdateComponent } from 'app/entities/activity-result-split/activity-result-split-update.component';
import { ActivityResultSplitService } from 'app/entities/activity-result-split/activity-result-split.service';
import { ActivityResultSplit } from 'app/shared/model/activity-result-split.model';

describe('Component Tests', () => {
  describe('ActivityResultSplit Management Update Component', () => {
    let comp: ActivityResultSplitUpdateComponent;
    let fixture: ComponentFixture<ActivityResultSplitUpdateComponent>;
    let service: ActivityResultSplitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JHipster6105TestModule],
        declarations: [ActivityResultSplitUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ActivityResultSplitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActivityResultSplitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActivityResultSplitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ActivityResultSplit(123);
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
        const entity = new ActivityResultSplit();
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
