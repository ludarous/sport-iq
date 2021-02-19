import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JHipster6105TestModule } from '../../../test.module';
import { OrganisationMembershipDetailComponent } from 'app/entities/organisation-membership/organisation-membership-detail.component';
import { OrganisationMembership } from 'app/shared/model/organisation-membership.model';

describe('Component Tests', () => {
  describe('OrganisationMembership Management Detail Component', () => {
    let comp: OrganisationMembershipDetailComponent;
    let fixture: ComponentFixture<OrganisationMembershipDetailComponent>;
    const route = ({ data: of({ organisationMembership: new OrganisationMembership(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JHipster6105TestModule],
        declarations: [OrganisationMembershipDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OrganisationMembershipDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OrganisationMembershipDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load organisationMembership on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.organisationMembership).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
