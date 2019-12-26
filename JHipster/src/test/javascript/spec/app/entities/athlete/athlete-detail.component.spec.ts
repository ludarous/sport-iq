import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SportiqTestModule } from '../../../test.module';
import { AthleteDetailComponent } from 'app/entities/athlete/athlete-detail.component';
import { Athlete } from 'app/shared/model/athlete.model';

describe('Component Tests', () => {
  describe('Athlete Management Detail Component', () => {
    let comp: AthleteDetailComponent;
    let fixture: ComponentFixture<AthleteDetailComponent>;
    const route = ({ data: of({ athlete: new Athlete(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportiqTestModule],
        declarations: [AthleteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AthleteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AthleteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.athlete).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
