import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Data } from '@angular/router';

import { SportiqTestModule } from '../../../test.module';
import { AthleteActivityResultSplitComponent } from 'app/entities/athlete-activity-result-split/athlete-activity-result-split.component';
import { AthleteActivityResultSplitService } from 'app/entities/athlete-activity-result-split/athlete-activity-result-split.service';
import { AthleteActivityResultSplit } from 'app/shared/model/athlete-activity-result-split.model';

describe('Component Tests', () => {
  describe('AthleteActivityResultSplit Management Component', () => {
    let comp: AthleteActivityResultSplitComponent;
    let fixture: ComponentFixture<AthleteActivityResultSplitComponent>;
    let service: AthleteActivityResultSplitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportiqTestModule],
        declarations: [AthleteActivityResultSplitComponent],
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
        .overrideTemplate(AthleteActivityResultSplitComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AthleteActivityResultSplitComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AthleteActivityResultSplitService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AthleteActivityResultSplit(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.athleteActivityResultSplits && comp.athleteActivityResultSplits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AthleteActivityResultSplit(123)],
            headers
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.athleteActivityResultSplits && comp.athleteActivityResultSplits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
