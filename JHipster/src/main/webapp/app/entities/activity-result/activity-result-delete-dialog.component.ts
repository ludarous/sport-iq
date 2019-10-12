import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActivityResult } from 'app/shared/model/activity-result.model';
import { ActivityResultService } from './activity-result.service';

@Component({
    selector: 'jhi-activity-result-delete-dialog',
    templateUrl: './activity-result-delete-dialog.component.html'
})
export class ActivityResultDeleteDialogComponent {
    activityResult: IActivityResult;

    constructor(
        private activityResultService: ActivityResultService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.activityResultService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'activityResultListModification',
                content: 'Deleted an activityResult'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-activity-result-delete-popup',
    template: ''
})
export class ActivityResultDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ activityResult }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ActivityResultDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.activityResult = activityResult;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
