import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SportIqTestModule } from '../../../test.module';
import { GroupMembershipUpdateComponent } from 'app/entities/group-membership/group-membership-update.component';
import { GroupMembershipService } from 'app/entities/group-membership/group-membership.service';
import { GroupMembership } from 'app/shared/model/group-membership.model';

describe('Component Tests', () => {
  describe('GroupMembership Management Update Component', () => {
    let comp: GroupMembershipUpdateComponent;
    let fixture: ComponentFixture<GroupMembershipUpdateComponent>;
    let service: GroupMembershipService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportIqTestModule],
        declarations: [GroupMembershipUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(GroupMembershipUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GroupMembershipUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupMembershipService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GroupMembership(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new GroupMembership();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
