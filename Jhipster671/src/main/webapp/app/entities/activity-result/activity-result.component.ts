import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IActivityResult } from 'app/shared/model/activity-result.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ActivityResultService } from './activity-result.service';
import { ActivityResultDeleteDialogComponent } from './activity-result-delete-dialog.component';

@Component({
  selector: 'jhi-activity-result',
  templateUrl: './activity-result.component.html'
})
export class ActivityResultComponent implements OnInit, OnDestroy {
  activityResults?: IActivityResult[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected activityResultService: ActivityResultService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.activityResultService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IActivityResult[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInActivityResults();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IActivityResult): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInActivityResults(): void {
    this.eventSubscriber = this.eventManager.subscribe('activityResultListModification', () => this.loadPage());
  }

  delete(activityResult: IActivityResult): void {
    const modalRef = this.modalService.open(ActivityResultDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.activityResult = activityResult;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IActivityResult[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/activity-result'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.activityResults = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
