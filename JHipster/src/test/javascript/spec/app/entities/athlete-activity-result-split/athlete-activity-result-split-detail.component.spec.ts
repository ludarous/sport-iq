import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SportiqTestModule } from '../../../test.module';
import { AthleteActivityResultSplitDetailComponent } from 'app/entities/athlete-activity-result-split/athlete-activity-result-split-detail.component';
import { AthleteActivityResultSplit } from 'app/shared/model/athlete-activity-result-split.model';

describe('Component Tests', () => {
  describe('AthleteActivityResultSplit Management Detail Component', () => {
    let comp: AthleteActivityResultSplitDetailComponent;
    let fixture: ComponentFixture<AthleteActivityResultSplitDetailComponent>;
    const route = ({ data: of({ athleteActivityResultSplit: new AthleteActivityResultSplit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportiqTestModule],
        declarations: [AthleteActivityResultSplitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AthleteActivityResultSplitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AthleteActivityResultSplitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.athleteActivityResultSplit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
