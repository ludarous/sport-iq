import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JHipster6105TestModule } from '../../../test.module';
import { LegalRepresentativeDetailComponent } from 'app/entities/legal-representative/legal-representative-detail.component';
import { LegalRepresentative } from 'app/shared/model/legal-representative.model';

describe('Component Tests', () => {
  describe('LegalRepresentative Management Detail Component', () => {
    let comp: LegalRepresentativeDetailComponent;
    let fixture: ComponentFixture<LegalRepresentativeDetailComponent>;
    const route = ({ data: of({ legalRepresentative: new LegalRepresentative(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JHipster6105TestModule],
        declarations: [LegalRepresentativeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LegalRepresentativeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LegalRepresentativeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load legalRepresentative on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.legalRepresentative).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
