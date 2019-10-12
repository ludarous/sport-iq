import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAthleteActivityResult } from 'app/shared/model/athlete-activity-result.model';
import { AthleteActivityResultService } from './athlete-activity-result.service';

@Component({
    selector: 'jhi-athlete-activity-result-delete-dialog',
    templateUrl: './athlete-activity-result-delete-dialog.component.html'
})
export class AthleteActivityResultDeleteDialogComponent {
    athleteActivityResult: IAthleteActivityResult;

    constructor(
        private athleteActivityResultService: AthleteActivityResultService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.athleteActivityResultService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'athleteActivityResultListModification',
                content: 'Deleted an athleteActivityResult'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-athlete-activity-result-delete-popup',
    template: ''
})
export class AthleteActivityResultDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ athleteActivityResult }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AthleteActivityResultDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.athleteActivityResult = athleteActivityResult;
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
