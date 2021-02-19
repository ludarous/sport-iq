import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JHipster6105TestModule } from '../../../test.module';
import { AgreementDetailComponent } from 'app/entities/agreement/agreement-detail.component';
import { Agreement } from 'app/shared/model/agreement.model';

describe('Component Tests', () => {
  describe('Agreement Management Detail Component', () => {
    let comp: AgreementDetailComponent;
    let fixture: ComponentFixture<AgreementDetailComponent>;
    const route = ({ data: of({ agreement: new Agreement(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JHipster6105TestModule],
        declarations: [AgreementDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AgreementDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AgreementDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load agreement on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.agreement).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
