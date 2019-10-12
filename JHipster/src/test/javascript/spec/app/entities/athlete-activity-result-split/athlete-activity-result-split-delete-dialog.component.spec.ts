/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SportiqTestModule } from '../../../test.module';
import { AthleteActivityResultSplitDeleteDialogComponent } from 'app/entities/athlete-activity-result-split/athlete-activity-result-split-delete-dialog.component';
import { AthleteActivityResultSplitService } from 'app/entities/athlete-activity-result-split/athlete-activity-result-split.service';

describe('Component Tests', () => {
    describe('AthleteActivityResultSplit Management Delete Component', () => {
        let comp: AthleteActivityResultSplitDeleteDialogComponent;
        let fixture: ComponentFixture<AthleteActivityResultSplitDeleteDialogComponent>;
        let service: AthleteActivityResultSplitService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SportiqTestModule],
                declarations: [AthleteActivityResultSplitDeleteDialogComponent]
            })
                .overrideTemplate(AthleteActivityResultSplitDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AthleteActivityResultSplitDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AthleteActivityResultSplitService);
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
