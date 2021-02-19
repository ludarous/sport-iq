import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { JHipster6105TestModule } from '../../../test.module';
import { BodyCharacteristicsComponent } from 'app/entities/body-characteristics/body-characteristics.component';
import { BodyCharacteristicsService } from 'app/entities/body-characteristics/body-characteristics.service';
import { BodyCharacteristics } from 'app/shared/model/body-characteristics.model';

describe('Component Tests', () => {
  describe('BodyCharacteristics Management Component', () => {
    let comp: BodyCharacteristicsComponent;
    let fixture: ComponentFixture<BodyCharacteristicsComponent>;
    let service: BodyCharacteristicsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JHipster6105TestModule],
        declarations: [BodyCharacteristicsComponent],
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
        .overrideTemplate(BodyCharacteristicsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BodyCharacteristicsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BodyCharacteristicsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BodyCharacteristics(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bodyCharacteristics && comp.bodyCharacteristics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BodyCharacteristics(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.bodyCharacteristics && comp.bodyCharacteristics[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
