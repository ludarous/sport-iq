import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JHipster6105TestModule } from '../../../test.module';
import { BodyCharacteristicsDetailComponent } from 'app/entities/body-characteristics/body-characteristics-detail.component';
import { BodyCharacteristics } from 'app/shared/model/body-characteristics.model';

describe('Component Tests', () => {
  describe('BodyCharacteristics Management Detail Component', () => {
    let comp: BodyCharacteristicsDetailComponent;
    let fixture: ComponentFixture<BodyCharacteristicsDetailComponent>;
    const route = ({ data: of({ bodyCharacteristics: new BodyCharacteristics(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JHipster6105TestModule],
        declarations: [BodyCharacteristicsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BodyCharacteristicsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BodyCharacteristicsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load bodyCharacteristics on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.bodyCharacteristics).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
