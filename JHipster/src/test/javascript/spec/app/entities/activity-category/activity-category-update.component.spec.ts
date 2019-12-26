import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SportiqTestModule } from '../../../test.module';
import { ActivityCategoryUpdateComponent } from 'app/entities/activity-category/activity-category-update.component';
import { ActivityCategoryService } from 'app/entities/activity-category/activity-category.service';
import { ActivityCategory } from 'app/shared/model/activity-category.model';

describe('Component Tests', () => {
  describe('ActivityCategory Management Update Component', () => {
    let comp: ActivityCategoryUpdateComponent;
    let fixture: ComponentFixture<ActivityCategoryUpdateComponent>;
    let service: ActivityCategoryService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportiqTestModule],
        declarations: [ActivityCategoryUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ActivityCategoryUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActivityCategoryUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActivityCategoryService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ActivityCategory(123);
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
        const entity = new ActivityCategory();
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
