import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { JHipster6105TestModule } from '../../../test.module';
import { UserActivityResultSplitComponent } from 'app/entities/user-activity-result-split/user-activity-result-split.component';
import { UserActivityResultSplitService } from 'app/entities/user-activity-result-split/user-activity-result-split.service';
import { UserActivityResultSplit } from 'app/shared/model/user-activity-result-split.model';

describe('Component Tests', () => {
  describe('UserActivityResultSplit Management Component', () => {
    let comp: UserActivityResultSplitComponent;
    let fixture: ComponentFixture<UserActivityResultSplitComponent>;
    let service: UserActivityResultSplitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JHipster6105TestModule],
        declarations: [UserActivityResultSplitComponent],
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
        .overrideTemplate(UserActivityResultSplitComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserActivityResultSplitComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserActivityResultSplitService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserActivityResultSplit(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userActivityResultSplits && comp.userActivityResultSplits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserActivityResultSplit(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userActivityResultSplits && comp.userActivityResultSplits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
