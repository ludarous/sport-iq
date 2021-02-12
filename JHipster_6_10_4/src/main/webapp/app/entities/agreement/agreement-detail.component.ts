import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAgreement } from 'app/shared/model/agreement.model';

@Component({
  selector: 'jhi-agreement-detail',
  templateUrl: './agreement-detail.component.html',
})
export class AgreementDetailComponent implements OnInit {
  agreement: IAgreement | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agreement }) => (this.agreement = agreement));
  }

  previousState(): void {
    window.history.back();
  }
}
