import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SportiqTestModule } from '../../../test.module';
import { WorkoutCategoryDetailComponent } from 'app/entities/workout-category/workout-category-detail.component';
import { WorkoutCategory } from 'app/shared/model/workout-category.model';

describe('Component Tests', () => {
  describe('WorkoutCategory Management Detail Component', () => {
    let comp: WorkoutCategoryDetailComponent;
    let fixture: ComponentFixture<WorkoutCategoryDetailComponent>;
    const route = ({ data: of({ workoutCategory: new WorkoutCategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportiqTestModule],
        declarations: [WorkoutCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(WorkoutCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkoutCategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.workoutCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
