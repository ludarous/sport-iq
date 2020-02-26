import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SportiqTestModule } from '../../../test.module';
import { AthleteActivityResultDetailComponent } from 'app/entities/athlete-activity-result/athlete-activity-result-detail.component';
import { AthleteActivityResult } from 'app/shared/model/athlete-activity-result.model';

describe('Component Tests', () => {
  describe('AthleteActivityResult Management Detail Component', () => {
    let comp: AthleteActivityResultDetailComponent;
    let fixture: ComponentFixture<AthleteActivityResultDetailComponent>;
    const route = ({ data: of({ athleteActivityResult: new AthleteActivityResult(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportiqTestModule],
        declarations: [AthleteActivityResultDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AthleteActivityResultDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AthleteActivityResultDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load athleteActivityResult on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.athleteActivityResult).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
