import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SportiqTestModule } from '../../../test.module';
import { AthleteWorkoutDetailComponent } from 'app/entities/athlete-workout/athlete-workout-detail.component';
import { AthleteWorkout } from 'app/shared/model/athlete-workout.model';

describe('Component Tests', () => {
  describe('AthleteWorkout Management Detail Component', () => {
    let comp: AthleteWorkoutDetailComponent;
    let fixture: ComponentFixture<AthleteWorkoutDetailComponent>;
    const route = ({ data: of({ athleteWorkout: new AthleteWorkout(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportiqTestModule],
        declarations: [AthleteWorkoutDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AthleteWorkoutDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AthleteWorkoutDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.athleteWorkout).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
