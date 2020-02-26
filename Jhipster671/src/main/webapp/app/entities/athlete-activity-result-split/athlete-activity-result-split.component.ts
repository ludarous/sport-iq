import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAthleteActivityResultSplit } from 'app/shared/model/athlete-activity-result-split.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AthleteActivityResultSplitService } from './athlete-activity-result-split.service';
import { AthleteActivityResultSplitDeleteDialogComponent } from './athlete-activity-result-split-delete-dialog.component';

@Component({
  selector: 'jhi-athlete-activity-result-split',
  templateUrl: './athlete-activity-result-split.component.html'
})
export class AthleteActivityResultSplitComponent implements OnInit, OnDestroy {
  athleteActivityResultSplits?: IAthleteActivityResultSplit[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected athleteActivityResultSplitService: AthleteActivityResultSplitService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.athleteActivityResultSplitService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IAthleteActivityResultSplit[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInAthleteActivityResultSplits();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAthleteActivityResultSplit): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAthleteActivityResultSplits(): void {
    this.eventSubscriber = this.eventManager.subscribe('athleteActivityResultSplitListModification', () => this.loadPage());
  }

  delete(athleteActivityResultSplit: IAthleteActivityResultSplit): void {
    const modalRef = this.modalService.open(AthleteActivityResultSplitDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.athleteActivityResultSplit = athleteActivityResultSplit;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IAthleteActivityResultSplit[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/athlete-activity-result-split'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.athleteActivityResultSplits = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
