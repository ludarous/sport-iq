import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAthleteWorkout } from 'app/shared/model/athlete-workout.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AthleteWorkoutService } from './athlete-workout.service';
import { AthleteWorkoutDeleteDialogComponent } from './athlete-workout-delete-dialog.component';

@Component({
  selector: 'jhi-athlete-workout',
  templateUrl: './athlete-workout.component.html'
})
export class AthleteWorkoutComponent implements OnInit, OnDestroy {
  athleteWorkouts?: IAthleteWorkout[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected athleteWorkoutService: AthleteWorkoutService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.athleteWorkoutService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IAthleteWorkout[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
        () => this.onError()
      );
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.ascending = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
      this.ngbPaginationPage = data.pagingParams.page;
      this.loadPage();
    });
    this.registerChangeInAthleteWorkouts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAthleteWorkout): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAthleteWorkouts(): void {
    this.eventSubscriber = this.eventManager.subscribe('athleteWorkoutListModification', () => this.loadPage());
  }

  delete(athleteWorkout: IAthleteWorkout): void {
    const modalRef = this.modalService.open(AthleteWorkoutDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.athleteWorkout = athleteWorkout;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IAthleteWorkout[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/athlete-workout'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.athleteWorkouts = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
