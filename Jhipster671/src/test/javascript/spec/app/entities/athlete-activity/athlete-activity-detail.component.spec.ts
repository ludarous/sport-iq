import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SportiqTestModule } from '../../../test.module';
import { AthleteActivityDetailComponent } from 'app/entities/athlete-activity/athlete-activity-detail.component';
import { AthleteActivity } from 'app/shared/model/athlete-activity.model';

describe('Component Tests', () => {
  describe('AthleteActivity Management Detail Component', () => {
    let comp: AthleteActivityDetailComponent;
    let fixture: ComponentFixture<AthleteActivityDetailComponent>;
    const route = ({ data: of({ athleteActivity: new AthleteActivity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportiqTestModule],
        declarations: [AthleteActivityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AthleteActivityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AthleteActivityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load athleteActivity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.athleteActivity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
