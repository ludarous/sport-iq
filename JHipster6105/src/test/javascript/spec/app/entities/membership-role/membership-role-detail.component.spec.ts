import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JHipster6105TestModule } from '../../../test.module';
import { MembershipRoleDetailComponent } from 'app/entities/membership-role/membership-role-detail.component';
import { MembershipRole } from 'app/shared/model/membership-role.model';

describe('Component Tests', () => {
  describe('MembershipRole Management Detail Component', () => {
    let comp: MembershipRoleDetailComponent;
    let fixture: ComponentFixture<MembershipRoleDetailComponent>;
    const route = ({ data: of({ membershipRole: new MembershipRole(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JHipster6105TestModule],
        declarations: [MembershipRoleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MembershipRoleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MembershipRoleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load membershipRole on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.membershipRole).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
