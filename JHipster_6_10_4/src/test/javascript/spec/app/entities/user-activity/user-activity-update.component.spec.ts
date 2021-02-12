import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SportIqTestModule } from '../../../test.module';
import { UserActivityUpdateComponent } from 'app/entities/user-activity/user-activity-update.component';
import { UserActivityService } from 'app/entities/user-activity/user-activity.service';
import { UserActivity } from 'app/shared/model/user-activity.model';

describe('Component Tests', () => {
  describe('UserActivity Management Update Component', () => {
    let comp: UserActivityUpdateComponent;
    let fixture: ComponentFixture<UserActivityUpdateComponent>;
    let service: UserActivityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportIqTestModule],
        declarations: [UserActivityUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserActivityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserActivityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserActivityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserActivity(123);
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
        const entity = new UserActivity();
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
