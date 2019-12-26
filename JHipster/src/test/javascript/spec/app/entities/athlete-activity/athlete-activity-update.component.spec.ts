import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SportiqTestModule } from '../../../test.module';
import { AthleteActivityUpdateComponent } from 'app/entities/athlete-activity/athlete-activity-update.component';
import { AthleteActivityService } from 'app/entities/athlete-activity/athlete-activity.service';
import { AthleteActivity } from 'app/shared/model/athlete-activity.model';

describe('Component Tests', () => {
  describe('AthleteActivity Management Update Component', () => {
    let comp: AthleteActivityUpdateComponent;
    let fixture: ComponentFixture<AthleteActivityUpdateComponent>;
    let service: AthleteActivityService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportiqTestModule],
        declarations: [AthleteActivityUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AthleteActivityUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AthleteActivityUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AthleteActivityService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AthleteActivity(123);
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
        const entity = new AthleteActivity();
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
