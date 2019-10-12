/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SportiqTestModule } from '../../../test.module';
import { AthleteUpdateComponent } from 'app/entities/athlete/athlete-update.component';
import { AthleteService } from 'app/entities/athlete/athlete.service';
import { Athlete } from 'app/shared/model/athlete.model';

describe('Component Tests', () => {
    describe('Athlete Management Update Component', () => {
        let comp: AthleteUpdateComponent;
        let fixture: ComponentFixture<AthleteUpdateComponent>;
        let service: AthleteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SportiqTestModule],
                declarations: [AthleteUpdateComponent]
            })
                .overrideTemplate(AthleteUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AthleteUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AthleteService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Athlete(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.athlete = entity;
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
                    const entity = new Athlete();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.athlete = entity;
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
