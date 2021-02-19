import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { JHipster6105TestModule } from '../../../test.module';
import { ActivityResultSplitComponent } from 'app/entities/activity-result-split/activity-result-split.component';
import { ActivityResultSplitService } from 'app/entities/activity-result-split/activity-result-split.service';
import { ActivityResultSplit } from 'app/shared/model/activity-result-split.model';

describe('Component Tests', () => {
  describe('ActivityResultSplit Management Component', () => {
    let comp: ActivityResultSplitComponent;
    let fixture: ComponentFixture<ActivityResultSplitComponent>;
    let service: ActivityResultSplitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JHipster6105TestModule],
        declarations: [ActivityResultSplitComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(ActivityResultSplitComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ActivityResultSplitComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ActivityResultSplitService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ActivityResultSplit(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.activityResultSplits && comp.activityResultSplits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ActivityResultSplit(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.activityResultSplits && comp.activityResultSplits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
