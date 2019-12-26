import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SportiqTestModule } from '../../../test.module';
import { AthleteActivityResultDeleteDialogComponent } from 'app/entities/athlete-activity-result/athlete-activity-result-delete-dialog.component';
import { AthleteActivityResultService } from 'app/entities/athlete-activity-result/athlete-activity-result.service';

describe('Component Tests', () => {
  describe('AthleteActivityResult Management Delete Component', () => {
    let comp: AthleteActivityResultDeleteDialogComponent;
    let fixture: ComponentFixture<AthleteActivityResultDeleteDialogComponent>;
    let service: AthleteActivityResultService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportiqTestModule],
        declarations: [AthleteActivityResultDeleteDialogComponent]
      })
        .overrideTemplate(AthleteActivityResultDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AthleteActivityResultDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AthleteActivityResultService);
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
