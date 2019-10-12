import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWorkoutCategory } from 'app/shared/model/workout-category.model';
import { WorkoutCategoryService } from './workout-category.service';

@Component({
    selector: 'jhi-workout-category-delete-dialog',
    templateUrl: './workout-category-delete-dialog.component.html'
})
export class WorkoutCategoryDeleteDialogComponent {
    workoutCategory: IWorkoutCategory;

    constructor(
        private workoutCategoryService: WorkoutCategoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.workoutCategoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'workoutCategoryListModification',
                content: 'Deleted an workoutCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-workout-category-delete-popup',
    template: ''
})
export class WorkoutCategoryDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ workoutCategory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(WorkoutCategoryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.workoutCategory = workoutCategory;
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
