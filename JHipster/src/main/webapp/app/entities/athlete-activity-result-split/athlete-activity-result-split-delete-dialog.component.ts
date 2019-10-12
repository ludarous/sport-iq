import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAthleteActivityResultSplit } from 'app/shared/model/athlete-activity-result-split.model';
import { AthleteActivityResultSplitService } from './athlete-activity-result-split.service';

@Component({
    selector: 'jhi-athlete-activity-result-split-delete-dialog',
    templateUrl: './athlete-activity-result-split-delete-dialog.component.html'
})
export class AthleteActivityResultSplitDeleteDialogComponent {
    athleteActivityResultSplit: IAthleteActivityResultSplit;

    constructor(
        private athleteActivityResultSplitService: AthleteActivityResultSplitService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.athleteActivityResultSplitService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'athleteActivityResultSplitListModification',
                content: 'Deleted an athleteActivityResultSplit'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-athlete-activity-result-split-delete-popup',
    template: ''
})
export class AthleteActivityResultSplitDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ athleteActivityResultSplit }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AthleteActivityResultSplitDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.athleteActivityResultSplit = athleteActivityResultSplit;
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
