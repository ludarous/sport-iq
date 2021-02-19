import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JHipster6105TestModule } from '../../../test.module';
import { UserActivityResultSplitUpdateComponent } from 'app/entities/user-activity-result-split/user-activity-result-split-update.component';
import { UserActivityResultSplitService } from 'app/entities/user-activity-result-split/user-activity-result-split.service';
import { UserActivityResultSplit } from 'app/shared/model/user-activity-result-split.model';

describe('Component Tests', () => {
  describe('UserActivityResultSplit Management Update Component', () => {
    let comp: UserActivityResultSplitUpdateComponent;
    let fixture: ComponentFixture<UserActivityResultSplitUpdateComponent>;
    let service: UserActivityResultSplitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JHipster6105TestModule],
        declarations: [UserActivityResultSplitUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserActivityResultSplitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserActivityResultSplitUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserActivityResultSplitService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserActivityResultSplit(123);
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
        const entity = new UserActivityResultSplit();
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
