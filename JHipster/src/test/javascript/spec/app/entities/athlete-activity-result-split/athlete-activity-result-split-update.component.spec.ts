/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SportiqTestModule } from '../../../test.module';
import { AthleteActivityResultSplitUpdateComponent } from 'app/entities/athlete-activity-result-split/athlete-activity-result-split-update.component';
import { AthleteActivityResultSplitService } from 'app/entities/athlete-activity-result-split/athlete-activity-result-split.service';
import { AthleteActivityResultSplit } from 'app/shared/model/athlete-activity-result-split.model';

describe('Component Tests', () => {
    describe('AthleteActivityResultSplit Management Update Component', () => {
        let comp: AthleteActivityResultSplitUpdateComponent;
        let fixture: ComponentFixture<AthleteActivityResultSplitUpdateComponent>;
        let service: AthleteActivityResultSplitService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SportiqTestModule],
                declarations: [AthleteActivityResultSplitUpdateComponent]
            })
                .overrideTemplate(AthleteActivityResultSplitUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AthleteActivityResultSplitUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AthleteActivityResultSplitService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AthleteActivityResultSplit(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.athleteActivityResultSplit = entity;
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
                    const entity = new AthleteActivityResultSplit();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.athleteActivityResultSplit = entity;
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
