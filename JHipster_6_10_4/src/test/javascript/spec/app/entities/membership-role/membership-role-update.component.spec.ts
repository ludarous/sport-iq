import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SportIqTestModule } from '../../../test.module';
import { MembershipRoleUpdateComponent } from 'app/entities/membership-role/membership-role-update.component';
import { MembershipRoleService } from 'app/entities/membership-role/membership-role.service';
import { MembershipRole } from 'app/shared/model/membership-role.model';

describe('Component Tests', () => {
  describe('MembershipRole Management Update Component', () => {
    let comp: MembershipRoleUpdateComponent;
    let fixture: ComponentFixture<MembershipRoleUpdateComponent>;
    let service: MembershipRoleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportIqTestModule],
        declarations: [MembershipRoleUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MembershipRoleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MembershipRoleUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MembershipRoleService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MembershipRole(123);
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
        const entity = new MembershipRole();
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
