import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SportIqTestModule } from '../../../test.module';
import { OrganisationMembershipUpdateComponent } from 'app/entities/organisation-membership/organisation-membership-update.component';
import { OrganisationMembershipService } from 'app/entities/organisation-membership/organisation-membership.service';
import { OrganisationMembership } from 'app/shared/model/organisation-membership.model';

describe('Component Tests', () => {
  describe('OrganisationMembership Management Update Component', () => {
    let comp: OrganisationMembershipUpdateComponent;
    let fixture: ComponentFixture<OrganisationMembershipUpdateComponent>;
    let service: OrganisationMembershipService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportIqTestModule],
        declarations: [OrganisationMembershipUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OrganisationMembershipUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrganisationMembershipUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OrganisationMembershipService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OrganisationMembership(123);
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
        const entity = new OrganisationMembership();
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
