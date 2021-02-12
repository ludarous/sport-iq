import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGroupMembership } from 'app/shared/model/group-membership.model';

@Component({
  selector: 'jhi-group-membership-detail',
  templateUrl: './group-membership-detail.component.html',
})
export class GroupMembershipDetailComponent implements OnInit {
  groupMembership: IGroupMembership | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ groupMembership }) => (this.groupMembership = groupMembership));
  }

  previousState(): void {
    window.history.back();
  }
}
