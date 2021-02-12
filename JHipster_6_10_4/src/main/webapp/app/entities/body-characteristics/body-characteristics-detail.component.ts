import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBodyCharacteristics } from 'app/shared/model/body-characteristics.model';

@Component({
  selector: 'jhi-body-characteristics-detail',
  templateUrl: './body-characteristics-detail.component.html',
})
export class BodyCharacteristicsDetailComponent implements OnInit {
  bodyCharacteristics: IBodyCharacteristics | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bodyCharacteristics }) => (this.bodyCharacteristics = bodyCharacteristics));
  }

  previousState(): void {
    window.history.back();
  }
}
