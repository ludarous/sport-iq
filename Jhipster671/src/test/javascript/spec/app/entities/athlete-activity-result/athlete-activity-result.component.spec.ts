import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { SportiqTestModule } from '../../../test.module';
import { AthleteActivityResultComponent } from 'app/entities/athlete-activity-result/athlete-activity-result.component';
import { AthleteActivityResultService } from 'app/entities/athlete-activity-result/athlete-activity-result.service';
import { AthleteActivityResult } from 'app/shared/model/athlete-activity-result.model';

describe('Component Tests', () => {
  describe('AthleteActivityResult Management Component', () => {
    let comp: AthleteActivityResultComponent;
    let fixture: ComponentFixture<AthleteActivityResultComponent>;
    let service: AthleteActivityResultService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportiqTestModule],
        declarations: [AthleteActivityResultComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: {
                subscribe: (fn: (value: Data) => void) =>
                  fn({
                    pagingParams: {
                      predicate: 'id',
                      reverse: false,
                      page: 0
                    }
                  })
              }
            }
          }
        ]
      })
        .overrideTemplate(AthleteActivityResultComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AthleteActivityResultComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AthleteActivityResultService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AthleteActivityResult(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.athleteActivityResults && comp.athleteActivityResults[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AthleteActivityResult(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.athleteActivityResults && comp.athleteActivityResults[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
