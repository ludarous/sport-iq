import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserEvent } from 'app/shared/model/user-event.model';

@Component({
  selector: 'jhi-user-event-detail',
  templateUrl: './user-event-detail.component.html',
})
export class UserEventDetailComponent implements OnInit {
  userEvent: IUserEvent | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userEvent }) => (this.userEvent = userEvent));
  }

  previousState(): void {
    window.history.back();
  }
}
