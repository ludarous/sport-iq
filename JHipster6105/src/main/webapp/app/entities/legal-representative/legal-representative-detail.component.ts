import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILegalRepresentative } from 'app/shared/model/legal-representative.model';

@Component({
  selector: 'jhi-legal-representative-detail',
  templateUrl: './legal-representative-detail.component.html',
})
export class LegalRepresentativeDetailComponent implements OnInit {
  legalRepresentative: ILegalRepresentative | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ legalRepresentative }) => (this.legalRepresentative = legalRepresentative));
  }

  previousState(): void {
    window.history.back();
  }
}
