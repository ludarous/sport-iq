import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAthleteActivity } from 'app/shared/model/athlete-activity.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AthleteActivityService } from './athlete-activity.service';
import { AthleteActivityDeleteDialogComponent } from './athlete-activity-delete-dialog.component';

@Component({
  selector: 'jhi-athlete-activity',
  templateUrl: './athlete-activity.component.html'
})
export class AthleteActivityComponent implements OnInit, OnDestroy {
  athleteActivities?: IAthleteActivity[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected athleteActivityService: AthleteActivityService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.athleteActivityService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IAthleteActivity[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInAthleteActivities();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAthleteActivity): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAthleteActivities(): void {
    this.eventSubscriber = this.eventManager.subscribe('athleteActivityListModification', () => this.loadPage());
  }

  delete(athleteActivity: IAthleteActivity): void {
    const modalRef = this.modalService.open(AthleteActivityDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.athleteActivity = athleteActivity;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IAthleteActivity[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/athlete-activity'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.athleteActivities = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
