import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IActivityResultSplit } from 'app/shared/model/activity-result-split.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ActivityResultSplitService } from './activity-result-split.service';
import { ActivityResultSplitDeleteDialogComponent } from './activity-result-split-delete-dialog.component';

@Component({
  selector: 'jhi-activity-result-split',
  templateUrl: './activity-result-split.component.html'
})
export class ActivityResultSplitComponent implements OnInit, OnDestroy {
  activityResultSplits?: IActivityResultSplit[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected activityResultSplitService: ActivityResultSplitService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.activityResultSplitService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IActivityResultSplit[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInActivityResultSplits();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IActivityResultSplit): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInActivityResultSplits(): void {
    this.eventSubscriber = this.eventManager.subscribe('activityResultSplitListModification', () => this.loadPage());
  }

  delete(activityResultSplit: IActivityResultSplit): void {
    const modalRef = this.modalService.open(ActivityResultSplitDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.activityResultSplit = activityResultSplit;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IActivityResultSplit[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/activity-result-split'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.activityResultSplits = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
