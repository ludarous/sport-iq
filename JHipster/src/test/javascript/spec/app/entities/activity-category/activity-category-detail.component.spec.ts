import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SportiqTestModule } from '../../../test.module';
import { ActivityCategoryDetailComponent } from 'app/entities/activity-category/activity-category-detail.component';
import { ActivityCategory } from 'app/shared/model/activity-category.model';

describe('Component Tests', () => {
  describe('ActivityCategory Management Detail Component', () => {
    let comp: ActivityCategoryDetailComponent;
    let fixture: ComponentFixture<ActivityCategoryDetailComponent>;
    const route = ({ data: of({ activityCategory: new ActivityCategory(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportiqTestModule],
        declarations: [ActivityCategoryDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ActivityCategoryDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ActivityCategoryDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.activityCategory).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
