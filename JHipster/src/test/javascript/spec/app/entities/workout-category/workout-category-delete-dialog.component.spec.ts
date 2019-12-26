import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SportiqTestModule } from '../../../test.module';
import { WorkoutCategoryDeleteDialogComponent } from 'app/entities/workout-category/workout-category-delete-dialog.component';
import { WorkoutCategoryService } from 'app/entities/workout-category/workout-category.service';

describe('Component Tests', () => {
  describe('WorkoutCategory Management Delete Component', () => {
    let comp: WorkoutCategoryDeleteDialogComponent;
    let fixture: ComponentFixture<WorkoutCategoryDeleteDialogComponent>;
    let service: WorkoutCategoryService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportiqTestModule],
        declarations: [WorkoutCategoryDeleteDialogComponent]
      })
        .overrideTemplate(WorkoutCategoryDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(WorkoutCategoryDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(WorkoutCategoryService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
