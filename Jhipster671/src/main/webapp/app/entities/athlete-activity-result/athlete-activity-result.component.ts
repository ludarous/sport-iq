import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAthleteActivityResult } from 'app/shared/model/athlete-activity-result.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AthleteActivityResultService } from './athlete-activity-result.service';
import { AthleteActivityResultDeleteDialogComponent } from './athlete-activity-result-delete-dialog.component';

@Component({
  selector: 'jhi-athlete-activity-result',
  templateUrl: './athlete-activity-result.component.html'
})
export class AthleteActivityResultComponent implements OnInit, OnDestroy {
  athleteActivityResults?: IAthleteActivityResult[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected athleteActivityResultService: AthleteActivityResultService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.athleteActivityResultService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IAthleteActivityResult[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInAthleteActivityResults();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAthleteActivityResult): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAthleteActivityResults(): void {
    this.eventSubscriber = this.eventManager.subscribe('athleteActivityResultListModification', () => this.loadPage());
  }

  delete(athleteActivityResult: IAthleteActivityResult): void {
    const modalRef = this.modalService.open(AthleteActivityResultDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.athleteActivityResult = athleteActivityResult;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IAthleteActivityResult[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/athlete-activity-result'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.athleteActivityResults = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
