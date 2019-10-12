/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SportiqTestModule } from '../../../test.module';
import { AthleteActivityDeleteDialogComponent } from 'app/entities/athlete-activity/athlete-activity-delete-dialog.component';
import { AthleteActivityService } from 'app/entities/athlete-activity/athlete-activity.service';

describe('Component Tests', () => {
    describe('AthleteActivity Management Delete Component', () => {
        let comp: AthleteActivityDeleteDialogComponent;
        let fixture: ComponentFixture<AthleteActivityDeleteDialogComponent>;
        let service: AthleteActivityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SportiqTestModule],
                declarations: [AthleteActivityDeleteDialogComponent]
            })
                .overrideTemplate(AthleteActivityDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AthleteActivityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AthleteActivityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
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
                )
            );
        });
    });
});
