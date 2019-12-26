import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SportiqTestModule } from '../../../test.module';
import { AthleteEventDeleteDialogComponent } from 'app/entities/athlete-event/athlete-event-delete-dialog.component';
import { AthleteEventService } from 'app/entities/athlete-event/athlete-event.service';

describe('Component Tests', () => {
  describe('AthleteEvent Management Delete Component', () => {
    let comp: AthleteEventDeleteDialogComponent;
    let fixture: ComponentFixture<AthleteEventDeleteDialogComponent>;
    let service: AthleteEventService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportiqTestModule],
        declarations: [AthleteEventDeleteDialogComponent]
      })
        .overrideTemplate(AthleteEventDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AthleteEventDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AthleteEventService);
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
