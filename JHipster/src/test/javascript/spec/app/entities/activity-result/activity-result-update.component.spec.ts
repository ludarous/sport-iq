/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SportiqTestModule } from '../../../test.module';
import { ActivityResultUpdateComponent } from 'app/entities/activity-result/activity-result-update.component';
import { ActivityResultService } from 'app/entities/activity-result/activity-result.service';
import { ActivityResult } from 'app/shared/model/activity-result.model';

describe('Component Tests', () => {
    describe('ActivityResult Management Update Component', () => {
        let comp: ActivityResultUpdateComponent;
        let fixture: ComponentFixture<ActivityResultUpdateComponent>;
        let service: ActivityResultService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SportiqTestModule],
                declarations: [ActivityResultUpdateComponent]
            })
                .overrideTemplate(ActivityResultUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ActivityResultUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActivityResultService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ActivityResult(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.activityResult = entity;
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
                    const entity = new ActivityResult();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.activityResult = entity;
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
