import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAthleteActivity } from 'app/shared/model/athlete-activity.model';
import { AthleteActivityService } from './athlete-activity.service';

@Component({
    selector: 'jhi-athlete-activity-delete-dialog',
    templateUrl: './athlete-activity-delete-dialog.component.html'
})
export class AthleteActivityDeleteDialogComponent {
    athleteActivity: IAthleteActivity;

    constructor(
        private athleteActivityService: AthleteActivityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.athleteActivityService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'athleteActivityListModification',
                content: 'Deleted an athleteActivity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-athlete-activity-delete-popup',
    template: ''
})
export class AthleteActivityDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ athleteActivity }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AthleteActivityDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.athleteActivity = athleteActivity;
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
