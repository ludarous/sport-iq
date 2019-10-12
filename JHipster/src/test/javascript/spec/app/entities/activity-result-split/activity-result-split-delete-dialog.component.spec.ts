/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SportiqTestModule } from '../../../test.module';
import { ActivityResultSplitDeleteDialogComponent } from 'app/entities/activity-result-split/activity-result-split-delete-dialog.component';
import { ActivityResultSplitService } from 'app/entities/activity-result-split/activity-result-split.service';

describe('Component Tests', () => {
    describe('ActivityResultSplit Management Delete Component', () => {
        let comp: ActivityResultSplitDeleteDialogComponent;
        let fixture: ComponentFixture<ActivityResultSplitDeleteDialogComponent>;
        let service: ActivityResultSplitService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SportiqTestModule],
                declarations: [ActivityResultSplitDeleteDialogComponent]
            })
                .overrideTemplate(ActivityResultSplitDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ActivityResultSplitDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ActivityResultSplitService);
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
