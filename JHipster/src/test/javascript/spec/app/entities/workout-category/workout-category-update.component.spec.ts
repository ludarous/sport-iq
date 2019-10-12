/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SportiqTestModule } from '../../../test.module';
import { WorkoutCategoryUpdateComponent } from 'app/entities/workout-category/workout-category-update.component';
import { WorkoutCategoryService } from 'app/entities/workout-category/workout-category.service';
import { WorkoutCategory } from 'app/shared/model/workout-category.model';

describe('Component Tests', () => {
    describe('WorkoutCategory Management Update Component', () => {
        let comp: WorkoutCategoryUpdateComponent;
        let fixture: ComponentFixture<WorkoutCategoryUpdateComponent>;
        let service: WorkoutCategoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SportiqTestModule],
                declarations: [WorkoutCategoryUpdateComponent]
            })
                .overrideTemplate(WorkoutCategoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WorkoutCategoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WorkoutCategoryService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new WorkoutCategory(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.workoutCategory = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new WorkoutCategory();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.workoutCategory = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
