import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAthleteWorkout } from 'app/shared/model/athlete-workout.model';
import { AthleteWorkoutService } from './athlete-workout.service';

@Component({
    selector: 'jhi-athlete-workout-delete-dialog',
    templateUrl: './athlete-workout-delete-dialog.component.html'
})
export class AthleteWorkoutDeleteDialogComponent {
    athleteWorkout: IAthleteWorkout;

    constructor(
        private athleteWorkoutService: AthleteWorkoutService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.athleteWorkoutService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'athleteWorkoutListModification',
                content: 'Deleted an athleteWorkout'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-athlete-workout-delete-popup',
    template: ''
})
export class AthleteWorkoutDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ athleteWorkout }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AthleteWorkoutDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.athleteWorkout = athleteWorkout;
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
