import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SportIqTestModule } from '../../../test.module';
import { UserActivityResultUpdateComponent } from 'app/entities/user-activity-result/user-activity-result-update.component';
import { UserActivityResultService } from 'app/entities/user-activity-result/user-activity-result.service';
import { UserActivityResult } from 'app/shared/model/user-activity-result.model';

describe('Component Tests', () => {
  describe('UserActivityResult Management Update Component', () => {
    let comp: UserActivityResultUpdateComponent;
    let fixture: ComponentFixture<UserActivityResultUpdateComponent>;
    let service: UserActivityResultService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportIqTestModule],
        declarations: [UserActivityResultUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserActivityResultUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserActivityResultUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserActivityResultService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserActivityResult(123);
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
        const entity = new UserActivityResult();
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
