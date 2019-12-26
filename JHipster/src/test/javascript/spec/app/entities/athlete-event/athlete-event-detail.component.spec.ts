import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SportiqTestModule } from '../../../test.module';
import { AthleteEventDetailComponent } from 'app/entities/athlete-event/athlete-event-detail.component';
import { AthleteEvent } from 'app/shared/model/athlete-event.model';

describe('Component Tests', () => {
  describe('AthleteEvent Management Detail Component', () => {
    let comp: AthleteEventDetailComponent;
    let fixture: ComponentFixture<AthleteEventDetailComponent>;
    const route = ({ data: of({ athleteEvent: new AthleteEvent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportiqTestModule],
        declarations: [AthleteEventDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AthleteEventDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AthleteEventDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.athleteEvent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
