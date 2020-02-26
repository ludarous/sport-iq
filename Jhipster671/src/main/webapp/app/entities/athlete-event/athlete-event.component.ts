import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAthleteEvent } from 'app/shared/model/athlete-event.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AthleteEventService } from './athlete-event.service';
import { AthleteEventDeleteDialogComponent } from './athlete-event-delete-dialog.component';

@Component({
  selector: 'jhi-athlete-event',
  templateUrl: './athlete-event.component.html'
})
export class AthleteEventComponent implements OnInit, OnDestroy {
  athleteEvents?: IAthleteEvent[];
  eventSubscriber?: Subscription;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page!: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;

  constructor(
    protected athleteEventService: AthleteEventService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadPage(page?: number): void {
    const pageToLoad: number = page || this.page;

    this.athleteEventService
      .query({
        page: pageToLoad - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe(
        (res: HttpResponse<IAthleteEvent[]>) => this.onSuccess(res.body, res.headers, pageToLoad),
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
    this.registerChangeInAthleteEvents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAthleteEvent): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAthleteEvents(): void {
    this.eventSubscriber = this.eventManager.subscribe('athleteEventListModification', () => this.loadPage());
  }

  delete(athleteEvent: IAthleteEvent): void {
    const modalRef = this.modalService.open(AthleteEventDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.athleteEvent = athleteEvent;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected onSuccess(data: IAthleteEvent[] | null, headers: HttpHeaders, page: number): void {
    this.totalItems = Number(headers.get('X-Total-Count'));
    this.page = page;
    this.router.navigate(['/athlete-event'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.ascending ? 'asc' : 'desc')
      }
    });
    this.athleteEvents = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page;
  }
}
