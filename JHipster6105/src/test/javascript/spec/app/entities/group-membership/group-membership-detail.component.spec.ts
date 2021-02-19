import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JHipster6105TestModule } from '../../../test.module';
import { GroupMembershipDetailComponent } from 'app/entities/group-membership/group-membership-detail.component';
import { GroupMembership } from 'app/shared/model/group-membership.model';

describe('Component Tests', () => {
  describe('GroupMembership Management Detail Component', () => {
    let comp: GroupMembershipDetailComponent;
    let fixture: ComponentFixture<GroupMembershipDetailComponent>;
    const route = ({ data: of({ groupMembership: new GroupMembership(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JHipster6105TestModule],
        declarations: [GroupMembershipDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(GroupMembershipDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GroupMembershipDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load groupMembership on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.groupMembership).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
