import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SportiqTestModule } from '../../../test.module';
import { AthleteWorkoutUpdateComponent } from 'app/entities/athlete-workout/athlete-workout-update.component';
import { AthleteWorkoutService } from 'app/entities/athlete-workout/athlete-workout.service';
import { AthleteWorkout } from 'app/shared/model/athlete-workout.model';

describe('Component Tests', () => {
  describe('AthleteWorkout Management Update Component', () => {
    let comp: AthleteWorkoutUpdateComponent;
    let fixture: ComponentFixture<AthleteWorkoutUpdateComponent>;
    let service: AthleteWorkoutService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportiqTestModule],
        declarations: [AthleteWorkoutUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AthleteWorkoutUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AthleteWorkoutUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AthleteWorkoutService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AthleteWorkout(123);
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
        const entity = new AthleteWorkout();
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
